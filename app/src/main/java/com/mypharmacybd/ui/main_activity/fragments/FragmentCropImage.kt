package com.mypharmacybd.ui.main_activity.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentCropImageBinding
import java.io.File
import java.io.FileOutputStream

class FragmentCropImage : Fragment() {
    private var _binding: FragmentCropImageBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageAbsolutePath: String

    private val navArgs by navArgs<FragmentCropImageArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCropImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide cart from tob bar
        binding.topBar.cart.ivCart.visibility = View.GONE
        binding.topBar.cart.tvCartCounter.visibility = View.GONE
        binding.topBar.tvTitle.text = getString(R.string.crop_image)

        // get image file from nav args reference
        imageAbsolutePath = navArgs.imageFile

        binding.civCropImage.apply {
            setAspectRatio(1, 1)
            setImageBitmap(getImageBitmap(imageAbsolutePath))
            setMinCropResultSize(300, 300)
        }

        binding.btnSave.setOnClickListener {
            val bitmap = binding.civCropImage.croppedImage
            if (bitmap != null) {
                saveFile(imageAbsolutePath, bitmap)
            }
        }
        binding.btnRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveFile(absolutePath: String, bitmap: Bitmap) {
        val imageFile = File(absolutePath)
        val fileOutputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        findNavController().popBackStack()
    }

    private fun getImageBitmap(path: String): Bitmap {
        Log.d(TAG, "getImageBitmap: path = $path")
        BitmapFactory.decodeFile(path).also { bitmap ->
            return bitmap
        }
    }

    companion object {
        private const val TAG = "FragmentCropImage"

        @JvmStatic
        fun newInstance() = FragmentCropImage()
    }
}