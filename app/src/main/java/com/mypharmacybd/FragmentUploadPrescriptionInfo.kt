package com.mypharmacybd

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.data_models.address.DistrictResponse
import com.mypharmacybd.data_models.address.DivisionResponse
import com.mypharmacybd.data_models.address.UpazilaResponse
import com.mypharmacybd.data_models.prescription.PrescriptionObjectSubmit
import com.mypharmacybd.data_models.prescription.PrescriptionPCSubmit
import com.mypharmacybd.data_models.user.User
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.databinding.FragmentFregmentUploadPrescriptionInfoBinding
import com.mypharmacybd.network.internet.ConnectionChecker
import com.mypharmacybd.other.Common
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.PrescriptionContact
import com.mypharmacybd.user.Session
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_UPLOAD_IMAGE = "uploadImage"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUploadPrescriptionInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FragmentUploadPrescriptionInfo : Fragment(), PrescriptionContact.View {
    // TODO: Rename and change types of parameters
    private var fileMultipartUrl: String? = null
    private var param2: String? = null
    private var _binding: FragmentFregmentUploadPrescriptionInfoBinding? = null

    private val binding get() = _binding!!

    private var _user: User? = Session.userResponse?.data

    private val _divisionList = mutableListOf<String>()
    private val _districtList = mutableListOf<String>()
    private val _upazilaList = mutableListOf<String>()

    private lateinit var _divisionAdapter: ArrayAdapter<String>
    private lateinit var _districtAdapter: ArrayAdapter<String>
    private lateinit var _upazilaAdapter: ArrayAdapter<String>

    @Inject
    lateinit var presenter: PrescriptionContact.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentFregmentUploadPrescriptionInfoBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.getString(ARG_UPLOAD_IMAGE) != null){
                fileMultipartUrl = it.getString(ARG_UPLOAD_IMAGE)!!
                Toast.makeText(requireContext(), "Select your area to upload image..", Toast.LENGTH_LONG).show()
            }
            param2 = it.getString(ARG_PARAM2)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FregmentUploadPrescriptionInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentUploadPrescriptionInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_UPLOAD_IMAGE, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private const val TAG = "FragmentPrescriptionInfo"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(_user!= null){
            prepareSpinner()
        }


        presenter.setView(this)
        binding.btnUpdateOrder.setOnClickListener {
            if (ConnectionChecker.isConnected(requireContext())){
                if (formValidation()) {
                    presenter.onPrescriptionUpload(collectData())
                    Log.d(TAG,collectData().toString())
                }
            }
            else{
                Toast.makeText(requireContext(),"No internet access",Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
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
    private fun getTextFormSpinner(spinner: Spinner): String {
        val tvSelectedItem = spinner.selectedView as TextView
        return tvSelectedItem.text.toString()
    }
    private fun formValidation() : Boolean{
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


        return true
    }

    private fun collectData(): PrescriptionObjectSubmit = PrescriptionObjectSubmit(
         file= PrescriptionPCSubmit(fileMultipartUrl.toString()),
       // file = PrescriptionPCSubmit("it.getString(ARG_UPLOAD_IMAGE)"),
        division = presenter.getDivisionIdByName(getTextFormSpinner(binding.spnDivision)).toString(),
        district = presenter.getDistrictIdByName(getTextFormSpinner(binding.spnDistrict)).toString(),
        upazila = presenter.getUpazilaIdByName(getTextFormSpinner(binding.spnUpazila)).toString(),
        address = binding.edtAddress.text.toString(),

    )
    override fun showProgressBar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.loading.visibility = View.GONE
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

    override fun onPrescriptionUpdateComplete(valueSuccessObject: UserUpdateInfoResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("Success!")
            .setMessage(valueSuccessObject.message)
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { _, _ -> findNavController().popBackStack() }
            .show()
    }

    override fun onPrescriptionUpdateFailure() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed!")
            .setMessage("Failed to send information.. Try again ")
            .setCancelable(true)
            .setNegativeButton(
                "OK"
            ) { _, _ ->
                run {

                }
            }.show()
    }


//    private fun getBase64ImageString(bitmap: Bitmap):String{
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
//        val byteArray:ByteArray = stream.toByteArray()
//        val imageString =  "data:ip/jpeg;base64,${
//            Base64.encodeToString(
//                byteArray,
//                Base64.DEFAULT
//            )
//        }";
//        Log.d(TAG, "getBase64ImageString: $imageString")
//        return imageString
//    }
//
//    private fun getImageBitmap(path: String): Bitmap {
//        Log.d(TAG, "getImageBitmap: path = $path")
//        BitmapFactory.decodeFile(path).also { bitmap ->
//            return bitmap
//        }
//    }


}