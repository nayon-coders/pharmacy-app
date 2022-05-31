package com.mypharmacybd.ui.main_activity.fragments.order_list.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.JsonObject
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.databinding.FragmentOrderListBinding
import com.mypharmacybd.databinding.FragmentOrderListItemViewBinding
import com.mypharmacybd.network.internet.ConnectionChecker
import com.mypharmacybd.other.Common
import com.mypharmacybd.other.Constants
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.dialog.DialogConfirmAddCart
import com.mypharmacybd.ui.main_activity.fragments.categories.adapter.OrdersAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module.OrderListItemSupport
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.presenter.OrderListItemSupportPresenter
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.adapter.PrescriptionListAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage.PrescriptionViewResponse
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
 * Use the [OrderListItemViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OrderListItemViewFragment : Fragment(),OrderListItemSupport.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentOrderListItemViewBinding? = null
    private var order:Int? = null
    private val binding get() = _binding!!
    private  var prescriptionObjectData: PrescriptionViewResponse? = null
    @Inject
    lateinit var presenter: OrderListItemSupport.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            order = it.getInt("order")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListItemViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         presenter.setView(this)
        // set toolbar title and cart counter
        binding.appBarLayout.tvTitle.text = getString(R.string.details)
        if(order != null){
            if (ConnectionChecker.isConnected(requireContext())){
                lifecycleScope.launch {
                    presenter.getListOfMedicine(order!!)
                    presenter.getDetails(order!!)
                }
            }
            else{
                Toast.makeText(requireContext(),"No internet access", Toast.LENGTH_SHORT).show()
            }

        }

        binding.downloadPrescription.setOnClickListener {
            if (!ConnectionChecker.isConnected(requireContext())){
                Toast.makeText(requireContext(),"No internet access", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(prescriptionObjectData != null){
                showDialogToViewPrescription(prescriptionObjectData!!)
            }
            else if(order != null){
                presenter.downloadImagePrescription(order!!)
            }

        }

        binding.tvViewCancel.setOnClickListener {
            if (!ConnectionChecker.isConnected(requireContext())){
                Toast.makeText(requireContext(),"No internet access", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                order?.let { it1 -> presenter.cancelOrder(it1) }
            }

        }
        initCart()
        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.appBarLayout.cart.tvCartCounter.setOnClickListener {
            findNavController().navigate(R.id.action_orderListItemViewFragment_to_fragmentCart)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderListItemViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderListItemViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val TAG = "ListItemViewOrder"
    }

    override fun showProgressbar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.loading.visibility = View.GONE
    }

    override fun setDataListMedicineToView(allMedicine: PrescriptionAllMedicne) {
        if(allMedicine.data?.requestedProducts != null){
            binding.rvOrderDisplayList.isNestedScrollingEnabled = false
            binding.rvOrderDisplayList.adapter = PrescriptionListAdapter(this,requireContext(),allMedicine)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setDetailsOfPrescription(prescription: Prescription) {

      val orderData = prescription.data
      if(orderData != null){
          binding.tvOrderNumber.text = orderData.rqCode
          binding.tvStatus.text = orderData.status.toString()
          if(binding.tvStatus.text.toString() == "Canceled"){
              binding.tvStatus.setTextColor(Color.parseColor("#D83737"))
          }
          if(binding.tvStatus.text.toString() == "Delivered"){
              binding.tvStatus.setTextColor(Color.parseColor("#008F20"))
          }
          if(binding.tvStatus.text.toString() == "Processing"){
              binding.tvStatus.setTextColor(Color.parseColor("#FF03DAC5"))

          }
          if(binding.tvStatus.text.toString() == "Pending"){
              binding.tvViewCancel.visibility = View.VISIBLE
          }
          else{
              binding.tvViewCancel.visibility = View.GONE
          }
          binding.tvCreateDate.text = "Order Date: ${orderData.deliveryMan?.createdAt}"
          binding.tvDivisionValue.text = " ${orderData.division?.name}"
          binding.tvDistrictValue.text = " ${orderData.district?.name}"
          binding.tvUpazilaValue.text = " ${orderData.upazila?.name}"
          binding.tvAddressValue.text = " ${orderData.address}"
      }




    }

    override fun orderBuyClick(product: Product) {
        //Show Add to cart Confirmation Dialog
        DialogConfirmAddCart(product).show(
            requireActivity().supportFragmentManager,
            DialogConfirmAddCart.TAG
        )
    }

    private fun initCart() {
        binding.appBarLayout.cart.tvCartCounter.visibility = View.VISIBLE

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
            }


        }
    }

    override fun showDialogSuccessCancel(objectJson: JsonObject) {
        AlertDialog.Builder(requireContext())
            .setTitle("Success!")
            .setMessage(objectJson.get("message").asString)
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { _, _ -> findNavController().popBackStack() }
            .show()
    }

    override fun showViewPrescription(objectData: PrescriptionViewResponse) {
        prescriptionObjectData = objectData
        showDialogToViewPrescription(objectData)
    }

    private fun showDialogToViewPrescription(objectData: PrescriptionViewResponse) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_layout_prescription_view)
        val imageView : ImageView = dialog.findViewById(R.id.imagePrescription)

        lifecycleScope.launch {
            Glide.with(requireActivity())
                .load(Constants.WEB_BASE_URL + objectData.data?.get(0)?.file.toString())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(Common.getLoadingDrawable(requireContext(), centerRadius = 10f, strokeWidth = 2f))
                .centerInside()
                .into(imageView)

        }

        dialog.setCancelable(true)
        dialog.show()
    }

    override fun failedToDisplayPrescription() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed!")
            .setMessage("Unable to display prescription ..")
            .setCancelable(true)
            .setPositiveButton(
                "OK"
            ) { _, _ ->  Toast.makeText(requireContext(),"Try after some time" , Toast.LENGTH_SHORT).show()}
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}