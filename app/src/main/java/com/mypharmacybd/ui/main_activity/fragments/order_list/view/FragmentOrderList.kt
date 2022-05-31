package com.mypharmacybd.ui.main_activity.fragments.order_list.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.databinding.FragmentOrderListBinding
import com.mypharmacybd.ui.main_activity.fragments.categories.adapter.OrdersAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentOrderList : Fragment(), OrderListContract.View {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!
    private var adapter : OrdersAdapter? = null
    private var orderObject: GetOrderResponse? =null

    @Inject
    lateinit var presenter: OrderListContract.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)

        // set toolbar title and cart counter
        binding.appBarLayout.tvTitle.text = getString(R.string.order)
        presenter.getOrders()
        initCart()
        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.appBarLayout.cart.tvCartCounter.setOnClickListener {
        findNavController().navigate(R.id.action_fragmentOrderList_to_fragmentCart)
        }
    }




    override fun showProgressbar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.loading.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setDataToView(orders: GetOrderResponse) {
        orderObject = orders
        adapter = OrdersAdapter(this, requireContext(), orders)
        binding.rvOrder.adapter = adapter
        adapter?.setObject(orders)
        adapter?.notifyDataSetChanged()
    }

    override fun onCategoryClicked(orders: OrderData) {
        val b = Bundle()
        orders.id?.let { it.toInt().let { it1 -> b.putInt("id", it1) } }
        findNavController().navigate(R.id.action_fragmentOrderList_to_singleOrderItemViewFragment,b)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        orderObject?.let { adapter?.setObject(it) }
        adapter?.notifyDataSetChanged()
    }
    private fun initCart() {
        binding.appBarLayout.cart.tvCartCounter.visibility = View.VISIBLE

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: is called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: is called")
    }

    companion object{
        private const val TAG = "FragmentOrderList"
    }
}