package com.mypharmacybd.ui.main_activity.fragments.privacy_policy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentPrivacyPolicyBinding
import com.mypharmacybd.user.Cart

class FragmentPrivacyPolicy : Fragment() {
    private var _binding:FragmentPrivacyPolicyBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set title
        binding.topBar.tvTitle.text = getString(R.string.privacy_policy)

        // set pop back
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // set cart counter
        setCartCounter()
    }

    private fun setCartCounter(){
        Cart.cartListLiveData?.observe(viewLifecycleOwner){
            binding.topBar.cart.tvCartCounter.text = it.size.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}