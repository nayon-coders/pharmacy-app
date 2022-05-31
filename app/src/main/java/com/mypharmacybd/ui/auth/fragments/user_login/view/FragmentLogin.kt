package com.mypharmacybd.ui.auth.fragments.user_login.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.util.PatternsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentLoginBinding
import com.mypharmacybd.ui.auth.fragments.user_login.LoginContract
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentLogin : Fragment(), LoginContract.View {
    private var _binding:FragmentLoginBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: LoginContract.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set view to presenter
        presenter.setView(this)



        // after login button pressed
        view.findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            if (checkLoginCredential()) {
                val credentials: LoginCredentials = getLoginCredentials()
                presenter.onLoginButtonClicked(credentials)

            }
        }

        // after sig up pressed, show sign up fragment
        binding.tvSignUp.setOnClickListener {
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegistration()
            findNavController().navigate(action)

        }

        // TextChangeListener implementation to hide error in EditText
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtEmailLayout.isErrorEnabled) {
                    binding.edtEmailLayout.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        // TextChangeListener implementation to hide error in EditText
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtPasswordLayout.isErrorEnabled) {
                    binding.edtPasswordLayout.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    private fun checkLoginCredential(): Boolean {
        if (binding.edtEmail.text.isNullOrBlank()) {
            binding.edtEmailLayout.error = getString(R.string.message_blank_email)
            return false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(
                binding.edtEmail.text.toString().trim()
            ).matches()) {
            binding.edtEmailLayout.error = getString(R.string.message_invalid_email)
            return false
        }
        if (binding.edtPassword.text.isNullOrEmpty()) {
            binding.edtPasswordLayout.error = getString(R.string.message_blank_password)
            return false
        } else if (binding.edtPassword.text.toString().trim().length < 8) {
            binding.edtPasswordLayout.error = getString(R.string.message_password_length_error)
            return false
        }

        return true
    }

    private fun getLoginCredentials(): LoginCredentials {
        return LoginCredentials(
            email = binding.edtEmail.text.toString(),
            password = binding.edtPassword.text.toString()
        )
    }

    override fun showProgressbar() {
        requireActivity().runOnUiThread {
            requireActivity().findViewById<View>(R.id.loading).visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar() {
        requireActivity().runOnUiThread {
            requireActivity().findViewById<View>(R.id.loading).visibility = View.GONE
        }
    }

    override fun onSuccessLogin() {
        Log.d(TAG, "onSuccessLogin: is called")

        requireActivity().runOnUiThread {
            requireActivity().finish()
        }
    }

    override fun onUsernamePasswordError() {
        AlertDialog.Builder(requireContext()).setTitle("Error")
            .setMessage("Email or Password is incorrect. Please try again with correct email and " +
                    "password")
            .setPositiveButton("OK", null)
            .show()

        binding.edtEmail.text?.clear()
        binding.edtPassword.text?.clear()
    }

    override fun showErrorDialog(message:String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        public const val TAG = "FragmentLogin"

        @JvmStatic
        fun newInstance() = FragmentLogin()
    }
}