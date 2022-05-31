package com.mypharmacybd.ui.main_activity.fragments.instruction_prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentPrescriptionInstructionBinding
import com.mypharmacybd.user.Cart

class FragmentPrescriptionInstruction : Fragment() {

    private var _binding: FragmentPrescriptionInstructionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPrescriptionInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set title
        binding.topBar.tvTitle.text = getString(R.string.why_upload_a_prescription)

        // set pop back
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // set cart Counter
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