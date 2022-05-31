package com.mypharmacybd.ui.main_activity.fragments.home.view

import android.content.res.Resources
import android.os.Bundle
import android.os.StrictMode
import android.transition.Slide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.slider.SliderData
import com.mypharmacybd.databinding.FragmentHomeBinding
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.ui.dialog.DialogLoading
import com.mypharmacybd.ui.main_activity.fragments.home.HomeContract
import com.mypharmacybd.ui.main_activity.fragments.home.adaper.AdapterHomeMain
import com.mypharmacybd.user.Cart
import com.mypharmacybd.user.Session
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.immutableListOf
import javax.inject.Inject
import kotlin.math.log
import kotlin.math.roundToInt

@AndroidEntryPoint
class FragmentHome : Fragment(), HomeContract.View {
    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var toggle:ActionBarDrawerToggle

    private val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private val dialogLoading = DialogLoading.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        StrictMode.setThreadPolicy(policy)
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
        presenter.getProduct()

        initCart()

        val drawerLayout:DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.ivMenu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val density = Resources.getSystem().displayMetrics.density
        val displayWidth = Resources.getSystem().displayMetrics.widthPixels
        val displayHeight: Int = (((displayWidth/16.0) * 9.0) - (44 * density)).roundToInt()

        binding.imageSlider.layoutParams.height = displayHeight
        binding.imageSlider.requestLayout()



        binding.cart.ivCart.setOnClickListener{
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentCart()
            findNavController().navigate(action)
        }

        binding.uploadPrescription.setOnClickListener {
            if(Session.authToken.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Please login or register",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentUploadPrescription()
            findNavController().navigate(action)
        }

        binding.searchBox.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentSearch()
            findNavController().navigate(action)
        }


    }


    override fun setDataToMainRecyclerView(categories: Categories) {
        Log.d(TAG, "setDataToMainRecyclerView: Category Size =  ${categories.data.size}")
        binding.rvHomeMain.adapter =
            AdapterHomeMain(requireContext(), this, categories)



    }

    override fun setSliderData(sliderData: SliderData) {
        val sliderModels:MutableList<SlideModel> = mutableListOf()
        for(slider in sliderData.data){
            sliderModels.add(SlideModel(ApiConfig.webBaseUrl + slider.image, ScaleTypes.CENTER_CROP))
        }
        binding.imageSlider.setImageList(sliderModels)
    }

    override fun viewProductDetails(product: Product) {
        val action = FragmentHomeDirections.actionFragmentHomeToFragmentProductDetails(product)
        findNavController().navigate(action)
    }

    override fun onViewDetailsClicked(category: Category) {
        val action = FragmentHomeDirections.actionFragmentHomeToFragmentCategoryDetails(category)
        findNavController().navigate(action)
    }

    override fun showProgressBar() {
        if (dialogLoading.isVisible) return

        dialogLoading.show(
            requireActivity().supportFragmentManager,
            DialogLoading.TAG
        )
    }

    override fun hideProgressBar() {
        if (dialogLoading.isVisible) dialogLoading.dismiss()
    }

    private fun initCart() {
        binding.cart.tvCartCounter.visibility = View.VISIBLE

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.cart.tvCartCounter.text = it.size.toString()
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentHome()

        private const val TAG = "FragmentHome"
    }
}

