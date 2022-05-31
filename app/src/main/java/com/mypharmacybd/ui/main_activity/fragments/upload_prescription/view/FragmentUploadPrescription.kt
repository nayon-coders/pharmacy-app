package com.mypharmacybd.ui.main_activity.fragments.upload_prescription.view

import android.R.string
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentUploadPrescriptionBinding
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.R)
@Suppress("DEPRECATION")
class FragmentUploadPrescription : Fragment() {
    private var _binding: FragmentUploadPrescriptionBinding? = null
    private var fileOfMultiPart: File? = null
    private var urlString : String? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.R)
    private var cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if(it){

            binding.layoutTakePhoto.visibility = View.GONE
            binding.layoutPhotoViewer.visibility = View.VISIBLE
            setPicFromCamera()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private var galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                val selectedImage = data.data
                if (selectedImage != null) {

//                    fileOfMultiPart = getFileFromUri(requireActivity().contentResolver,
//                    selectedImage, requireActivity().cacheDir)
                    fileOfMultiPart = createFileFromUri(requireActivity().contentResolver,selectedImage)
                    setPicFromCamera()
                }
            }

        }
    }


    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadPrescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.tvTitle.text = getString(R.string.upload_prescription)
        binding.topBar.cart.ivCart.visibility = View.GONE
        binding.topBar.cart.tvCartCounter.visibility = View.GONE

        //dispatchTakeImageIntent()

        binding.btnRetry.setOnClickListener {
            implementBottomSheet(requireContext())
        }

        binding.btnUpload.setOnClickListener {

            if(fileOfMultiPart == null ||  urlString == null ){
                Toast.makeText(requireContext(), "Take the picture properly", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val b = Bundle()
            b.putString("uploadImage", urlString!!.trim())
            findNavController().navigate(R.id.action_fragmentUploadPrescription_to_fragmentUploadPrescriptionInfo,b)
        }

        binding.btnTakePhoto.setOnClickListener {
            implementBottomSheet(requireContext())
        }

        implementBottomSheet(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun implementBottomSheet(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_take_picture_options)

        bottomSheetDialog.findViewById<View>(R.id.optionCamera)?.setOnClickListener {
            dispatchCameraIntent()
        }
        bottomSheetDialog.findViewById<View>(R.id.optionStorage)?.setOnClickListener {
            dispatchTakeFileIntent()
        }

        bottomSheetDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun dispatchCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takeImageIntent ->

            takeImageIntent.resolveActivity(requireActivity().packageManager)?.also {
                fileOfMultiPart = createImageFile()


                fileOfMultiPart.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.mypharmacybd",
                        it!!
                    )

                    takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraResultLauncher.launch(photoURI)
                }
            }
        }
    }






    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = this.absolutePath
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun setPicFromCamera() {
        // get the dimension of the view
        val targetW = binding.ivPrescription.width
        val targetH = binding.ivPrescription.height

        Log.d(TAG, "setPic: target width = $targetW")
        Log.d(TAG, "setPic: target height = $targetH")


        val bmOptions = BitmapFactory.Options().apply {

            // get the dimension of the bitmaps
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW = outWidth
            val photoH = outHeight

            Log.d(TAG, "setPic: photo width = $photoW")
            Log.d(TAG, "setPic: photo height = $photoH")

            // determine how to scale down the image
            val scaleFactor: Int =
                1.coerceAtLeast((photoW / targetW).coerceAtMost(photoH / targetH))

            Log.d(TAG, "setPic: scale factor = $scaleFactor")

            // Decode the image file into into a bitmap sized to fill the view
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            binding.ivPrescription.setImageBitmap(bitmap)
             // e.g calling from activity lifecycle scope
            lifecycleScope.launch {
               urlString = getCompressedFile(fileOfMultiPart!!)?.path?.let { encodeImage(it) }
            }
            Log.d(TAG, "setPic: final bitmap width = ${bitmap.width}")
            Log.d(TAG, "setPic: final bitmap height = ${bitmap.height}")
            Log.d(TAG, "setPic: final bitmap size = ${bitmap.byteCount}")
        }
    }




    @SuppressLint("SimpleDateFormat")
    private fun createFileFromUri(contentResolver: ContentResolver, uri:Uri): File {
        val timeStamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        ).apply {
            currentPhotoPath = this.absolutePath
        }
        file.outputStream().use {
            contentResolver.openInputStream(uri)?.copyTo(it)
        }

        return file
    }

    private fun dispatchTakeFileIntent() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).also { takeImageIntent -> galleryResultLauncher.launch(takeImageIntent) }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUploadPrescription()
        private const val TAG = "FragmentUploadPrescript"
    }




    private fun getCompressedFile(file: File): File? {
        return try {
            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 50

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun encodeImage(path: String): String {
        val imagefile = File(path)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(imagefile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bm = BitmapFactory.decodeStream(fis)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.WEBP_LOSSY, 90, baos)
        val b = baos.toByteArray()

        var converted = Base64.encodeToString(b, Base64.NO_WRAP).replace('-', '+')
        converted = converted.replace('_', '/')
        converted = converted.substring(converted.lastIndexOf(',') + 1)

        //Base64.de
        return converted
    }

}