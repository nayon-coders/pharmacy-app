package com.mypharmacybd.ui.main_activity.fragments.account.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mypharmacybd.R
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.databinding.FragmentAccountBinding
import com.mypharmacybd.other.Common
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.auth.ActivityAuth
import com.mypharmacybd.ui.main_activity.fragments.account.AccountContract
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAccount : Fragment(), AccountContract.View {
    private var _binding:FragmentAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: AccountContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        binding.editProfile.setOnClickListener {
            val action = FragmentAccountDirections.actionFragmentAccountToFragmentUpdateUserInfo()
            findNavController().navigate(action)
        }
        binding.myOrder.setOnClickListener {
            val action = FragmentAccountDirections.actionFragmentAccountToFragmentOrderList()
            findNavController().navigate(action)
        }
//
//        binding.favouriteList.setOnClickListener {
//            val action = FragmentAccountDirections.actionFragmentAccountToFragmentUnderMaintenance()
//            findNavController().navigate(action)
//        }
//
//        binding.promotions.setOnClickListener {
//            val action = FragmentAccountDirections.actionFragmentAccountToFragmentUnderMaintenance()
//            findNavController().navigate(action)
//        }
        binding.myOrderPres.setOnClickListener {
            findNavController().navigate(FragmentAccountDirections.actionFragmentAccountToListOfPrescriptionsFragment())
        }
        binding.profileSettings.setOnClickListener {
            val action = FragmentAccountDirections.actionFragmentAccountToFragmentUpdateUserInfo()
            findNavController().navigate(action)
        }

        binding.logout.setOnClickListener {
            presenter.onLogoutClicked()
        }

        binding.btnLogin.setOnClickListener {
            val action = FragmentAccountDirections.actionFragmentAccountToActivityAuth(ActivityAuth.AUTH_LOGIN)
            findNavController().navigate(action)
        }


        binding.btnSignUp.setOnClickListener {
            val action = FragmentAccountDirections.
            actionFragmentAccountToActivityAuth(ActivityAuth.AUTH_REGISTRATION)

            findNavController().navigate(action)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUserDataToView(userResponse: UserResponse) {
        showProfileLayout()

        Log.d(TAG, "setUserDataToView: user image =  ${userResponse.data?.image}")

        val url:String = Constants.WEB_BASE_URL + userResponse.data?.image

        Glide.with(requireContext())
            .load( url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(Common.getLoadingDrawable(requireContext(), centerRadius = 20f, strokeWidth = 4f))
            .centerInside()
            .error(R.drawable.ic_profile_100)
            .into(binding.civProfileImage)

        binding.tvName.text  = userResponse.data?.name ?: ""
        binding.tvPhone.text  = userResponse.data?.phone ?: ""
        binding.tvEmail.text  = userResponse.data?.email ?: ""
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            binding.loading.loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            binding.loading.loading.visibility = View.GONE
        }

    }

    override fun showLoginLayout() {
        requireActivity().runOnUiThread {
            binding.profileLayout.visibility = View.GONE
            binding.loginLayout.visibility = View.VISIBLE
        }
    }

    override fun showProfileLayout() {
        requireActivity().runOnUiThread {
            binding.loginLayout.visibility = View.GONE
            binding.profileLayout.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getSession()
    }

    companion object {

        private const val TAG = "FragmentAccount"

        @JvmStatic
        fun newInstance() = FragmentAccount()
    }
}