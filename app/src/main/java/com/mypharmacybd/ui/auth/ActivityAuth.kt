package com.mypharmacybd.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.mypharmacybd.R
import com.mypharmacybd.ui.auth.fragments.user_login.view.FragmentLoginDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAuth : AppCompatActivity() {

    private val navArgs by navArgs<ActivityAuthArgs>()

    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.authNavHost) as NavHostFragment

        navController = navHostFragment.navController



        when(navArgs.action){
            AUTH_LOGIN -> loadLoginFragment()
            AUTH_REGISTRATION ->loadRegistrationFragment()
        }


    }

    private fun loadLoginFragment(){

    }

    private fun loadRegistrationFragment(){
        val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegistration()
        navController.navigate(action)
    }

    companion object{
        const val AUTH_LOGIN:Int = 1
        const val AUTH_REGISTRATION:Int = 0
    }
}