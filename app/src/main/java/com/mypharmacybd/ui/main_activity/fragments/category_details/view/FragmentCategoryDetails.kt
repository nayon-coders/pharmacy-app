package com.mypharmacybd.ui.main_activity.fragments.category_details.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.databinding.FragmentCategoryDetailsBinding
import com.mypharmacybd.ui.dialog.DialogConfirmAddCart
import com.mypharmacybd.ui.main_activity.fragments.category_details.CategoryDetailsContract
import com.mypharmacybd.ui.main_activity.fragments.category_details.adapter.ListProductAdapter
import com.mypharmacybd.ui.main_activity.fragments.product_details.FragmentProductDetails
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCategoryDetails : Fragment(), CategoryDetailsContract.View {

    private var _binding:FragmentCategoryDetailsBinding? = null

    @Inject
    lateinit var presenter:CategoryDetailsContract.Presenter

    private val navArgs by navArgs<FragmentCategoryDetailsArgs>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category:Category = navArgs.category

        binding.tvCategoryName.text = category.name

        presenter.setView(this)

        presenter.getCategoryData(category.slug.toString())

        binding.topBar.searchBox.setOnClickListener {
            val action = FragmentCategoryDetailsDirections.actionFragmentCategoryDetailsToFragmentSearch()
            findNavController().navigate(action)
        }

        binding.topBar.ivBack.setOnClickListener{
            findNavController().popBackStack()
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentCategoryDetails()
    }

    override fun setDataToView(products: Products) {
        binding.rvCategoryProduct.adapter = ListProductAdapter(this, products, requireContext())
    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun setNoProductVisibility(isVisible: Boolean) {
        if(isVisible){
            binding.noProduct.visibility = View.VISIBLE
        } else {
            binding.noProduct.visibility = View.GONE
        }
    }

    override fun onAddToCartClicked(product: Product) {

        val action = FragmentCategoryDetailsDirections
            .actionFragmentCategoryDetailsToFragmentProductDetails(product)
        findNavController().navigate(action)
    }
}