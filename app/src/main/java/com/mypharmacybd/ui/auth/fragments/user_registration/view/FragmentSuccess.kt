package com.mypharmacybd.ui.auth.fragments.user_registration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mypharmacybd.R

class FragmentSuccess : Fragment() {

    private val navArgs by navArgs<FragmentSuccessArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message = "Hi ${navArgs.name}! " + getString(R.string.registration_success_message)
        view.findViewById<TextView>(R.id.tvSuccessMessage).text = message

        view.findViewById<View>(R.id.btnBackLogin).setOnClickListener {
            val action = FragmentSuccessDirections.actionFragmentSuccessToFragmentLogin()
            findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentSuccess()
    }
}