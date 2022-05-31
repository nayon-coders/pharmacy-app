package com.mypharmacybd.ui.main_activity.fragments.terms_condition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentTermsConditionBinding
import com.mypharmacybd.user.Cart

class FragmentTermsCondition : Fragment() {

    private var _binding: FragmentTermsConditionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTermsConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set title
        binding.topBar.tvTitle.text = getString(R.string.terms_condition)

        // set pop back
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // init cart counter
        initCartCounter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initCartCounter() {
        Cart.cartListLiveData?.observe(viewLifecycleOwner) {
            binding.topBar.cart.tvCartCounter.text = it.size.toString()
        }
    }

}