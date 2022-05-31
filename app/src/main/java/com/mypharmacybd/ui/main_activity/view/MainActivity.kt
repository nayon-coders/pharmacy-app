package com.mypharmacybd.ui.main_activity.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mypharmacybd.R
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.internet.ConnectionChecker
import com.mypharmacybd.ui.main_activity.MainContract
import com.mypharmacybd.user.Cart
import com.mypharmacybd.user.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter:MainContract.Presenter

    lateinit var bottomNavigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ConnectionChecker.isConnected(this)) showToast("Internet Connected")
        else showToast("No Internet")

        // setup bottom navigation
        setupBottomNav()
        setUpDrawer()
        Log.d(TAG, "onCreate: is called")

        presenter.setView(this)
        presenter.getCart()
        presenter.getSession()


    }

    override fun setCart(listCartEntity: LiveData<List<CartEntity>>) {
            Cart.cartListLiveData = listCartEntity
    }

    override fun setSession(sessionEntity: SessionEntity, userResponse: UserResponse) {
        Session.userResponse = userResponse
        Session.loginStatus = true
        Session.authToken = sessionEntity.accessToken
    }

    private  fun setUpDrawer(){
        val drawer : NavigationView= findViewById(R.id.navigationView)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        drawer.setupWithNavController(navController)

    }
    private fun setupBottomNav(){
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        findViewById<View>(R.id.menuUploadPrescription).setOnClickListener {

            if(Session.authToken.isNullOrEmpty()){
                Toast.makeText(this,"Please login or register",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            navController.navigate(R.id.fragmentUploadPrescription)
        }

        findViewById<View>(R.id.fragmentAccount).setOnClickListener {
            navController.navigate(R.id.fragmentAccount)
        }
        findViewById<View>(R.id.fragmentCart).setOnClickListener {
            navController.navigate(R.id.fragmentCart)
        }
        findViewById<View>(R.id.fragmentHome).setOnClickListener {
            navController.navigate(R.id.fragmentHome)
        }
        findViewById<View>(R.id.fragmentCategories).setOnClickListener {
            navController.navigate(R.id.fragmentCategories)
        }

    }

    fun closeDrawer(){

    }


    private fun showToast(msg: String) {
        Toast.makeText(this, " $msg", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}