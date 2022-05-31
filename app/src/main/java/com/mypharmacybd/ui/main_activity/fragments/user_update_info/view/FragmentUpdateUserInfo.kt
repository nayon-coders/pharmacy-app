package com.mypharmacybd.ui.main_activity.fragments.user_update_info.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mypharmacybd.R
import com.mypharmacybd.data_models.address.DistrictResponse
import com.mypharmacybd.data_models.address.DivisionResponse
import com.mypharmacybd.data_models.address.UpazilaResponse
import com.mypharmacybd.data_models.user.User
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.databinding.FragmentUpdateUserInfoBinding
import com.mypharmacybd.other.Common
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.UpdateInfoContract
import com.mypharmacybd.user.Session
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentUpdateUserInfo : Fragment(), UpdateInfoContract.View {

    private var _binding: FragmentUpdateUserInfoBinding? = null
    private var _user: User? = Session.userResponse?.data

    private val _divisionList = mutableListOf<String>()
    private val _districtList = mutableListOf<String>()
    private val _upazilaList = mutableListOf<String>()

    private lateinit var _divisionAdapter: ArrayAdapter<String>
    private lateinit var _districtAdapter: ArrayAdapter<String>
    private lateinit var _upazilaAdapter: ArrayAdapter<String>

    @Inject
    lateinit var presenter: UpdateInfoContract.Presenter

    private val binding get() = _binding!!

    private lateinit var bottomSheetDialog:BottomSheetDialog

    private var profilePhotoAbsolutePath:String? = null

    private val cameraIntentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                val action = profilePhotoAbsolutePath?.let { path ->
                    FragmentUpdateUserInfoDirections
                        .actionFragmentUpdateUserInfoToFragmentCropImage(path)
                }
                if(action != null) {
                    findNavController().navigate(action)
                }
            }
        }
    }

    private val galleryIntentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val data = it.data
            if(data != null){
                data.data?.let { uri ->
                    createFileFromUri(requireActivity().contentResolver,
                        uri
                    )
                }

                val action = profilePhotoAbsolutePath?.let{path->
                    FragmentUpdateUserInfoDirections
                        .actionFragmentUpdateUserInfoToFragmentCropImage(path)
                }

                action?.let {direction->
                    findNavController().navigate(direction)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (_user != null) {
            binding.edtName.setText(_user!!.name)
            binding.edtPhone.setText(_user!!.phone)
            binding.edtEmail.setText(_user!!.email)

            val imageUrl = Constants.WEB_BASE_URL + _user!!.image
            Glide.with(requireContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(Common.getLoadingDrawable(requireContext(), centerRadius = 20f, strokeWidth = 4f))
                .centerInside()
                .error(R.drawable.ic_profile_100)
                .into(binding.civProfileImage)

            Log.d(TAG, "onViewCreated: user image = $imageUrl")

            if (_user!!.details != null) {
                binding.edtAddress.setText(_user!!.details?.address_1)
                binding.edtPostalCode.setText(_user!!.details?.zip)
            }
        }

        prepareSpinner()

        presenter.setView(this)

        binding.btnUpdateOrder.setOnClickListener {
            if (checkFormValidation()) {
                presenter.onUpdateOrderClicked(collectData())
            }
        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnChangePhoto.setOnClickListener {
            showBottomSheet()
        }
    }

    override fun onResume() {
        super.onResume()
        if(profilePhotoAbsolutePath != null){
            setPictureToProfile()
        }
    }

    private fun showBottomSheet() {
         bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.setContentView(R.layout.bottom_sheet_take_picture_options)

        bottomSheetDialog.findViewById<View>(R.id.optionCamera)?.setOnClickListener {
            dispatchCameraIntent()
        }

        bottomSheetDialog.findViewById<View>(R.id.optionStorage)?.setOnClickListener {
            dispatchGalleryIntent()
        }

        bottomSheetDialog.show()
    }
    private fun hideBottomSheet(){
        bottomSheetDialog.hide()
    }

    private fun dispatchCameraIntent() {
        hideBottomSheet()
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takeImageIntent ->
            takeImageIntent.resolveActivity(requireActivity().packageManager).also {
                val photoFile: File = createPhotoFile()

                photoFile.also {
                    val photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.mypharmacybd",
                        it
                    )
                    takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    cameraIntentResult.launch(takeImageIntent)
                }

            }
        }
    }

    private fun dispatchGalleryIntent(){
        hideBottomSheet()
        Intent(Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            galleryIntentResult.launch(it)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createPhotoFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            profilePhotoAbsolutePath = this.absolutePath
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createFileFromUri(contentResolver: ContentResolver, uri:Uri){
        val timeStamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        ).apply {
            profilePhotoAbsolutePath = this.absolutePath
        }
        file.outputStream().use {
            contentResolver.openInputStream(uri)?.copyTo(it)
        }
    }

    private fun setPictureToProfile() {

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(profilePhotoAbsolutePath, this)

            inJustDecodeBounds = false
            inSampleSize = 1
        }

        BitmapFactory.decodeFile(profilePhotoAbsolutePath, bmOptions).also { bitmap->
            binding.civProfileImage.setImageBitmap(bitmap)
        }

    }

    private fun prepareSpinner() {
        _divisionList.add("<--Division-->")
        _districtList.add("--")
        _upazilaList.add("--")

        _divisionAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            _divisionList
        )
        _divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDivision.adapter = _divisionAdapter

        _districtAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            _districtList
        )
        _districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDistrict.adapter = _districtAdapter

        _upazilaAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            _upazilaList
        )
        _upazilaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnUpazila.adapter = _upazilaAdapter


    }

    private fun checkFormValidation(): Boolean {
        if (binding.edtName.text.isNullOrBlank()) {
            binding.edtName.error = getString(R.string.required)
            binding.edtName.requestFocus()
            return false
        }

        if (binding.edtPhone.text.isNullOrBlank()) {
            binding.edtPhone.error = getString(R.string.required)
            binding.edtPhone.requestFocus()
            return false
        }

        if (binding.edtEmail.text.isNullOrBlank()) {
            binding.edtEmail.error = getString(R.string.required)
            binding.edtEmail.requestFocus()
            return false
        }

        if (binding.edtAddress.text.isNullOrBlank()) {
            binding.edtAddress.error = getString(R.string.required)
            binding.edtAddress.requestFocus()
            return false
        }

        if (binding.spnDivision.selectedItemPosition == 0) {
            Common.dialogOK(requireContext(), R.string.division_district_upazila_error)
            Common.setSpinnerError(getString(R.string.not_selected), binding.spnDivision)
            return false
        } else {
            Log.d(
                TAG,
                "checkFormValidation: Selected name = ${getTextFormSpinner(binding.spnDivision)}"
            )
        }

        if (binding.spnDistrict.selectedItemPosition == 0) {
            Common.setSpinnerError(getString(R.string.not_selected), binding.spnDistrict)
            Common.dialogOK(requireContext(), R.string.division_district_upazila_error)
            return false
        }

        if (binding.spnUpazila.selectedItemPosition == 0) {
            Common.setSpinnerError(getString(R.string.not_selected), binding.spnUpazila)
            Common.dialogOK(requireContext(), R.string.division_district_upazila_error)
            return false
        }

        if (binding.edtPostalCode.text.isNullOrBlank()) {
            binding.edtPostalCode.error = getString(R.string.required)
            binding.edtPostalCode.requestFocus()
            return false
        }

        if (binding.edtConfirmPassword.text.isNullOrBlank()) {
            binding.edtConfirmPassword.text.clear()
            binding.edtConfirmPassword.error = getString(R.string.required)
            binding.edtConfirmPassword.requestFocus()
            return false
        }

        return true
    }

    private fun collectData(): UserUpdateInfo = UserUpdateInfo(
        name = binding.edtName.text.toString(),
        phone = binding.edtPhone.text.toString(),
        division = presenter.getDivisionIdByName(getTextFormSpinner(binding.spnDivision)),
        district = presenter.getDistrictIdByName(getTextFormSpinner(binding.spnDistrict)),
        upazila = presenter.getUpazilaIdByName(getTextFormSpinner(binding.spnUpazila)),
        address = binding.edtAddress.text.toString(),
        zip = binding.edtPostalCode.text.toString().toInt(),
        image = getBase64ImageString(binding.civProfileImage.drawToBitmap(Bitmap.Config.ARGB_8888)),
        email = binding.edtEmail.text.toString(),
        password = binding.edtConfirmPassword.text.toString(),
        passwordConfirmation = binding.edtConfirmPassword.text.toString()
    )

    private fun getTextFormSpinner(spinner: Spinner): String {
        val tvSelectedItem = spinner.selectedView as TextView
        return tvSelectedItem.text.toString()
    }

    private fun getBase64ImageString(bitmap: Bitmap):String{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteArray:ByteArray = stream.toByteArray()
        val imageString =  "data:image/jpeg;base64,${encodeToString(byteArray, Base64.DEFAULT)}";
        Log.d(TAG, "getBase64ImageString: $imageString")
        return imageString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setDivisionToView(divisionResponse: DivisionResponse) {

        for (div in divisionResponse.divisionList) {
            _divisionList.add(div.name)
        }
        _divisionAdapter.notifyDataSetChanged()

        // select user saved division
        val id: Int? = Session.userResponse?.data?.details?.division?.id
        val selectedPosition: Int? = id?.let { divisionResponse.getDivisionPosition(it) }
        if (selectedPosition != null) {
            binding.spnDivision.setSelection(selectedPosition + 1)
        }

        // Division Selected Listener
        binding.spnDivision.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    //_user?.details?.division = divisionResponse.divisionList[position - 1]
                    presenter.getDistrictsByDivision(divisionResponse.divisionList[position - 1])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun setDistrictsToView(districtResponse: DistrictResponse) {
        _districtList.clear()
        _districtList.add("-- District --")
        for (dis in districtResponse.districtList) {
            _districtList.add(dis.name)
        }
        _districtAdapter.notifyDataSetChanged()
        binding.spnDistrict.setSelection(0)

        // select user set district
        val id: Int? = Session.userResponse?.data?.details?.district?.id
        val selectedPosition: Int? = id?.let { districtResponse.getDistrictPosition(it) }
        if (selectedPosition != null) {
            binding.spnDistrict.setSelection(selectedPosition + 1)
        }

        binding.spnDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    //_user?.details?.district = districtResponse.districtList[position-1]
                    presenter.getUpazilasByDistrict(districtResponse.districtList[position - 1])

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun setUpazilasToView(upazilaResponse: UpazilaResponse) {
        _upazilaList.clear()
        _upazilaList.add("-- Upazila --")
        for (upz in upazilaResponse.upazilaList) {
            _upazilaList.add(upz.name)
        }

        _upazilaAdapter.notifyDataSetChanged()
        binding.spnUpazila.setSelection(0)

        // select user set district
        val id: Int? = _user?.details?.upazila?.id
        val selectedPosition: Int? = id?.let { upazilaResponse.getUpazilaPosition(it) }
        if (selectedPosition != null) {
            binding.spnUpazila.setSelection(selectedPosition + 1)
        }
    }

    override fun onProfileUpdateComplete(userUpdateInfoResponse: UserUpdateInfoResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("Success!")
            .setMessage("Your profile information successfully updated.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { _, _ -> findNavController().popBackStack() }
            .show()
    }

    override fun onProfileUpdateFailed() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed!")
            .setMessage("Failed to update your profile information. Try again or contact with us.")
            .setCancelable(true)
            .setNegativeButton(
                "OK"
            ) { _, _ ->
                run {

                }
            }.show()
    }

    override fun showProgressBar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.loading.visibility = View.GONE
    }




    companion object {
        @JvmStatic
        fun newInstance() = FragmentUpdateUserInfo()

        private const val TAG = "FragmentUpdateUserInfo"

    }
}