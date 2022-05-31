package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.FragmentUploadPrescriptionInfo
import com.mypharmacybd.R
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.databinding.FragmentListOfPrescriptionsBinding
import com.mypharmacybd.databinding.FragmentOrderListBinding
import com.mypharmacybd.network.internet.ConnectionChecker
import com.mypharmacybd.ui.main_activity.fragments.categories.adapter.OrdersAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.adapter.PrescriptionListAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.adapter.PrescriptionListMedicineAdapter
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact.PrescriptionListContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfPrescriptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListOfPrescriptionsFragment : Fragment(),PrescriptionListContact.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentListOfPrescriptionsBinding? = null
    private var prescriptionAll: AllPrescription? = null
    private  var adapter : PrescriptionListMedicineAdapter? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var presenter: PrescriptionListContact.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListOfPrescriptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)

        // set toolbar title and cart counter
        binding.appBarLayout.tvTitle.text = getString(R.string.prescription)

//        if (Cart.cartListLiveData != null) {
//            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
//                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
//            }
//        }
        if (ConnectionChecker.isConnected(requireContext())){
            presenter.getOrders()
        }
        else{
            Toast.makeText(requireContext(),"No internet access", Toast.LENGTH_SHORT).show()
        }


        initCart()
        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.appBarLayout.cart.tvCartCounter.setOnClickListener {
            val action = ListOfPrescriptionsFragmentDirections.actionListOfPrescriptionsFragmentToFragmentCart()
            findNavController().navigate(action)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListOfPrescriptionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListOfPrescriptionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private const val TAG = "PrescriptionList"
    }

    override fun showProgressbar() {
        binding.progressBar.loading.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.loading.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setDataToView(prescriptions: AllPrescription) {
        prescriptionAll = prescriptions
        adapter = PrescriptionListMedicineAdapter(this, requireContext(), prescriptions)
        binding.rvOrder.adapter = adapter
        adapter?.setObjectData(prescriptions)
        adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        prescriptionAll?.let { adapter?.setObjectData(it) }
        adapter?.notifyDataSetChanged()
    }

    override fun onPrescriptionItemClicked(id : Int) {
        val bundle = Bundle()
        bundle.putInt("order",id)
        findNavController().navigate(R.id.action_listOfPrescriptionsFragment_to_orderListItemViewFragment,bundle)
    }

    private fun initCart() {
        binding.appBarLayout.cart.tvCartCounter.visibility = View.VISIBLE

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}