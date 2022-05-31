package com.mypharmacybd.ui.main_activity.fragments.checkout.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.data_models.address.District
import com.mypharmacybd.data_models.address.Division
import com.mypharmacybd.data_models.address.Upazila
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.databinding.FragmentCheckoutBinding
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.ui.main_activity.fragments.checkout.CheckoutContract
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCheckout : Fragment(), CheckoutContract.View {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: CheckoutContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set view in presenter
        presenter.setView(this)

        // set title
        binding.topBar.tvTitle.text = getString(R.string.place_order)

        // hide cart icon
        hideCartIcon()

        // set pop back
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // get division list
        presenter.getDivision()

        // on change division spinner
        binding.spnDivision.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onDivisionSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing Selected
            }

        }

        // on Change district spinner
        binding.spnDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onDistrictSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        // on change upazila spinner item
        binding.spnUpazila.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onUpazilaSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        binding.btnPlaceOrder.setOnClickListener {
            requestConfirmation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.destroyView()
    }

    private fun hideCartIcon() {
        binding.topBar.cart.root.visibility = View.GONE
    }

    private fun requestConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Place Order")
            .setMessage("Are you sure to place order?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> presenter.onConfirmPlaceOrder() }
            .setNegativeButton("No Now", null)
            .show()
    }

    override fun setDivisionToSpinner(divisionList: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            divisionList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDivision.adapter = adapter
    }

    override fun setDistrictToSpinner(districtList: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            districtList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDistrict.adapter = adapter
    }

    override fun setUpazilaToSpinner(upazilaList: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            upazilaList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnUpazila.adapter = adapter
    }

    override fun clearDistrictSpinner() {
        binding.spnDistrict.adapter = null
    }

    override fun clearUpazilaSpinner() {
        binding.spnUpazila.adapter = null
    }

    override fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun prepareAllData(): PostOrder? {

        val selectedDivision: Division? = presenter.getSelectedDivision()
        val selectedDistrict: District? = presenter.getSelectedDistrict()
        val selectedUpazila: Upazila? = presenter.getSelectedUpazila()

        if (selectedDivision == null) {
            showDialog(
                "Division Not Selected",
                "Please Select Your Division from dropdown to continue"
            )
            return null
        }

        if (selectedDistrict == null) {
            showDialog(
                "District Not Selected",
                "Please select your District from dropdown to continue"
            )
            return null
        }

        if (selectedUpazila == null) {
            showDialog(
                "Upazila Not Selected",
                "Please select your Upazila form dropdown to continue"
            )
            return null
        }

        if (binding.edtZipCode.text.isNullOrBlank()) {
            showDialog(
                "Zip/Postal Code Required", "You must provide your zip/postal code"
            )
            binding.edtZipCode.requestFocus()
            binding.edtZipCode.error = "Required"
            return null
        }

        if (binding.edtAddress.text.isNullOrBlank()) {
            showDialog(
                "Address Required",
                "Your address field is empty. You must provide you address here"
            )
            binding.edtAddress.requestFocus()
            binding.edtAddress.error = "Required"
            return null
        }


        return PostOrder(
            paymentType = "cash",
            address1 = binding.edtAddress.text.toString(),
            division = selectedDivision.id,
            district = selectedDistrict.id,
            upazila = selectedUpazila.id,
            zip = binding.edtZipCode.text.toString().toInt(),
            shipping = 45,
            productList = getCartProduct()
        )
    }

    private fun getCartProduct(): List<PostOrder.Product> {
        val cartProduct: List<CartEntity>? = Cart.cartListLiveData?.value
        val listPostProduct = ArrayList<PostOrder.Product>()

        if (cartProduct != null) {
            for (entity in cartProduct) {
                listPostProduct.add(
                    PostOrder.Product(
                        Id = entity.productID,
                        quantity = entity.quantity.toString(),
                        price = entity.new_price?.toDouble() ?: 0.0
                    )
                )
            }
        }

        return listPostProduct
    }

    override fun orderSuccess() {
        AlertDialog.Builder(requireContext())
            .setTitle("Success!")
            .setMessage("Your order has been successfully submitted. Thank you.")
            .setPositiveButton("OK") { _, _ ->
                presenter.clearCart()
                val action = FragmentCheckoutDirections.actionFragmentCheckoutToFragmentHome()
                findNavController().navigate(action)
            }
            .show()
    }
}