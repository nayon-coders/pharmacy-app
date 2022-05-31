package com.mypharmacybd.ui.auth.fragments.user_registration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentRegistrationBinding
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.auth.fragments.user_registration.RegistrationContract
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.ui.dialog.DialogLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentRegistration : Fragment(), RegistrationContract.View {
    private var _binding:FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val dialogLoading = DialogLoading.newInstance()


    @Inject
    lateinit var presenter: RegistrationContract.Presenter

    @Inject
    lateinit var apiServices: ApiServices


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)

        // sign up button clicked
        view.findViewById<MaterialButton>(R.id.btnSignUp).setOnClickListener {
            if (checkFormDataValidity()) {
                presenter.onSignUpButtonClicked(
                    RegistrationData(
                        name = binding.edtName.text.toString().trim(),
                        email = binding.edtEmail.text.toString().trim(),
                        phone = "+880${binding.edtPhone.text.toString().trim()}",
                        password = binding.edtPassword.text.toString().trim()
                    )
                )
            }
        }

        // Back to Login Page
        view.findViewById<TextView>(R.id.tvLogin).setOnClickListener {
            val action = FragmentRegistrationDirections.actionFragmentRegistrationToFragmentLogin()
            findNavController().navigate(action)

        }

        // Back Operation Implementation
        view.findViewById<ShapeableImageView>(R.id.ivBack).setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (dialogLoading.isVisible) dialogLoading.dismiss()
        _binding = null
    }

    /**
     * Return True if all input field from user registration form is valid
     */
    private fun checkFormDataValidity(): Boolean {
        if (binding.edtName.text.isNullOrBlank()) {
            binding.edtNameLayout.error = getString(R.string.message_blank_person_name)
            return false
        }

        if (binding.edtEmail.text.isNullOrBlank()) {
            binding.edtEmailLayout.error = getString(R.string.message_blank_email)
            return false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(
                binding.edtEmail.text.toString()
            ).matches()) {
            binding.edtEmailLayout.error = getString(R.string.message_invalid_email)
            return false
        }

        if (binding.edtPhone.text.isNullOrBlank()) {
            binding.edtPhoneLayout.error = getString(R.string.message_blank_phone)
            return false
        } else if ("+880${binding.edtPhone.text.toString().trim()}".length != 14) {
            binding.edtPhoneLayout.error = getString(R.string.message_invalid_phone)
            return false
        }

        if (binding.edtPassword.text.isNullOrBlank()) {
            binding.edtPasswordLayout.error = getString(R.string.message_blank_password)
            return false
        } else if (binding.edtPassword.text.toString().trim().length < 8) {
            binding.edtPasswordLayout.error = getString(R.string.message_password_length_error)
            return false
        }

        return true
    }


    override fun showProgressBar() {
        dialogLoading.isCancelable = false
        dialogLoading.show(requireActivity().supportFragmentManager, DialogLoading.TAG)
    }

    override fun hideProgressBar() {
        dialogLoading.dismiss()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun openVeriFrag() {

    }

    override fun showSuccess(name:String) {
        val action = FragmentRegistrationDirections.actionFragmentRegistrationToFragmentSuccess(name)
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentRegistration()
        const val TAG = "FragmentRegistration"
    }
}