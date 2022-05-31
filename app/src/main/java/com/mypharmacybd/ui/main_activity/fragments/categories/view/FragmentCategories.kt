package com.mypharmacybd.ui.main_activity.fragments.categories.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.databinding.FragmentCategoriesBinding
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract
import com.mypharmacybd.ui.main_activity.fragments.categories.adapter.CategoriesAdapter
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCategories : Fragment(), CategoriesContract.View {

    private var _binding: FragmentCategoriesBinding? = null

    @Inject
    lateinit var presenter: CategoriesContract.Presenter


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)

        // set toolbar title and cart counter
        binding.appBarLayout.tvTitle.text = getString(R.string.categories)

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
            }
        }
        presenter.getCategories()

        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun setDataToView(categories: Categories) {
        binding.rvCategory.adapter = CategoriesAdapter(this, requireContext(), categories)
    }

    override fun onCategoryClicked(category: Category) {
        val action = FragmentCategoriesDirections
            .actionFragmentCategoriesToFragmentCategoryDetails(category)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: is called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: is called")
    }

    companion object{
        private const val TAG = "FragmentCategories"
    }
}