package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentOrderListBinding
import com.mypharmacybd.databinding.FragmentSingleOrderItemViewBinding
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.FragmentOrderList
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.contact.ItemOrderContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass.ViewOrderItemData
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module.OrderListItemSupport
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleOrderItemViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SingleOrderItemViewFragment : Fragment(),ItemOrderContact.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var id: Int? = null
    private var _binding: FragmentSingleOrderItemViewBinding? = null

    private val binding get() = _binding!!
    @Inject
    lateinit var presenter: ItemOrderContact.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            id = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSingleOrderItemViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        binding.appBarLayout.tvTitle.text = getString(R.string.my_order_details)
        initCart()

        lifecycleScope.launch {
            id?.let { presenter.getOrderFromItem(it) }
        }

        binding.appBarLayout.cart.tvCartCounter.setOnClickListener {
            findNavController().navigate(R.id.action_singleOrderItemViewFragment_to_fragmentCart)
        }

        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.tvViewCancel.setOnClickListener {
            lifecycleScope.launch {
                id?.let { it1 -> presenter.cancelOrderItem(it1) }
            }

        }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleOrderItemViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleOrderItemViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showProgressbar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.loading.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun setDataToView(order: ViewOrderItemData) {
        val dataOrder = order.data
        if(dataOrder != null){
            binding.baselineScroll.visibility = View.VISIBLE
            binding.tvOrderNumber.text = dataOrder.orderNumber
            binding.tvCreateDate.text = "Order date: ${dataOrder.date}"
            binding.tvStatusVal.text = dataOrder.status
            binding.tvOderStatusValue.text = dataOrder.status
            binding.tvPaymentStatusValue.text = dataOrder.paymentStatus
            binding.tvPaymentTypeValue.text = "Unknown"
            binding.tvAddressValue.text = "Address: ${dataOrder.address}, ${dataOrder.upazila?.name}, ${dataOrder.district?.name}, ${dataOrder.division?.name}, Zip - ${dataOrder.zip}"

            if(dataOrder.deliveryMan != null ){
                binding.footer.visibility = View.VISIBLE
                binding.tvNameValue.text = dataOrder.deliveryMan?.name
                binding.tvEmailValue.text = dataOrder.deliveryMan?.email
                binding.tvPhoneNumberValue.text = dataOrder.deliveryMan?.phone
            }
            else{
                binding.footer.visibility = View.GONE
            }


            if(binding.tvStatusVal.text.toString() == "Canceled"){
                binding.tvStatusVal.setTextColor(Color.parseColor("#D83737"))
            }
            if(binding.tvStatusVal.text.toString() == "Delivered"){
                binding.tvStatusVal.setTextColor(Color.parseColor("#008F20"))
            }
            if(binding.tvStatusVal.text.toString() == "Processing"){
                binding.tvStatusVal.setTextColor(Color.parseColor("#FF03DAC5"))
            }



            if(dataOrder.status == "Pending"){
                binding.tvViewCancel.visibility = View.VISIBLE
            }
            else{
                binding.tvViewCancel.visibility = View.GONE
            }
            if(dataOrder.discount != null){
                binding.tvDiscountValue.text = dataOrder.discount + " Tk"
            }
            else{
                binding.tvDiscountValue.text = "Not Available"
            }

            if(dataOrder.amount != null) binding.tvTotalValue.text = dataOrder.amount + " Tk"
            if(dataOrder.shipping != null) binding.tvShippingValue.text = dataOrder.shipping + " Tk"
        }
        else{
            binding.baselineScroll.visibility = View.GONE

            AlertDialog.Builder(requireContext())
                .setTitle("Failed!")
                .setMessage("Unable to display anything..")
                .setCancelable(true)
                .setPositiveButton(
                    "OK"
                ) { _, _ ->  Toast.makeText(requireContext(),"Try after some time" , Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                }
                .show()
        }


    }

    override fun successCancelling(orderCancel: JsonObject) {
        AlertDialog.Builder(requireContext())
            .setTitle("Success!")
            .setMessage(orderCancel.get("message").asString)
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { _, _ -> findNavController().popBackStack() }
            .show()
    }

    override fun failedToCancel() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed!")
            .setMessage("Unable to cancel ..")
            .setCancelable(true)
            .setPositiveButton(
                "OK"
            ) { _, _ ->  Toast.makeText(requireContext(),"Try after some time" , Toast.LENGTH_SHORT).show()}
            .show()
    }
}