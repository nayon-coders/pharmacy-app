package com.mypharmacybd.ui.maintenance

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.JsonObject
import com.mypharmacybd.R
import com.mypharmacybd.data_models.address.DistrictResponse
import com.mypharmacybd.data_models.address.DivisionResponse
import com.mypharmacybd.data_models.address.UpazilaResponse
import com.mypharmacybd.data_models.user.User
import com.mypharmacybd.databinding.FragmentUnderMaintenanceBinding
import com.mypharmacybd.databinding.FragmentUploadPrescriptionBinding
import com.mypharmacybd.ui.main_activity.fragments.FragmentCropImage
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.PrescriptionContact
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.view.FragmentUpdateUserInfo
import com.mypharmacybd.user.Session
import java.io.ByteArrayOutputStream
import java.io.File

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_UPLOAD_IMAGE = "uploadImage"
private const val ARG_PARAM2 = "param2"
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FragmentUnderMaintenance : Fragment() {
    private var fileMultipartUrl: String? = null
    private var param2: String? = null
    private var _binding: FragmentUnderMaintenanceBinding? = null
    private var fileOfMultiPart: File? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUnderMaintenanceBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.getString(ARG_UPLOAD_IMAGE) != null){
                fileMultipartUrl = it.getString(ARG_UPLOAD_IMAGE)
                fileOfMultiPart = File(fileMultipartUrl)
                Toast.makeText(requireContext(), "Select your area to upload image..", Toast.LENGTH_LONG).show()
            }
            param2 = it.getString(ARG_PARAM2)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() = FragmentUnderMaintenance()
        private const val TAG = "FragmentPrescriptionInfo"
    }




}