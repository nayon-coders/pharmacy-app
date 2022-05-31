package com.mypharmacybd.ui.main_activity.fragments.search.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.databinding.FragmentSearchBinding
import com.mypharmacybd.ui.main_activity.fragments.search.SearchContract
import com.mypharmacybd.ui.main_activity.fragments.search.view.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSearch : Fragment(),SearchContract.View {
    private var _binding:FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // inject presenter
    @Inject
    lateinit var presenter: SearchContract.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)

        binding.tvSearch.setOnClickListener {
            if(!binding.edtSearch.text.isNullOrBlank()){
                presenter.onSearchClicked(binding.edtSearch.text.toString())
                hideSoftKeyboard(requireView(), requireActivity())
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    
    private fun hideSoftKeyboard(view: View, activity: FragmentActivity){
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setSearchResponse(searchResponse: SearchResponse) {
        if(searchResponse.productList.isNullOrEmpty()){
            binding.tvMessage.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
        } else {
            binding.rvSearch.adapter = SearchResultAdapter(searchResponse.productList, this,
                requireContext())
            binding.rvSearch.visibility = View.VISIBLE
            binding.tvMessage.visibility = View.GONE
        }
    }

    override fun onAddCartClicked(product:Product) {
        val action = FragmentSearchDirections.actionFragmentSearchToFragmentProductDetails(product)
        findNavController().navigate(action)
    }

    override fun showLoading() {
        binding.loading.root.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loading.root.visibility = View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentSearch()
    }
}