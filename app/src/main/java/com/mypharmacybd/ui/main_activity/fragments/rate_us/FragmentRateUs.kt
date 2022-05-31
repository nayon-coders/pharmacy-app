package com.mypharmacybd.ui.main_activity.fragments.rate_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentRateUsBinding
import com.mypharmacybd.user.Cart


class FragmentRateUs : Fragment() {
    private var _binding:FragmentRateUsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRateUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set title
        binding.topBar.tvTitle.text = getString(R.string.rate_us)

        // set pop back
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // set cart counter
        setCartCounter()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCartCounter(){
        Cart.cartListLiveData?.observe(viewLifecycleOwner){
            binding.topBar.cart.tvCartCounter.text = it.size.toString()
        }
    }

}