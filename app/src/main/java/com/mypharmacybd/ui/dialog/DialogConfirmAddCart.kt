package com.mypharmacybd.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.databinding.DialogConfirmAddCartBinding
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DialogConfirmAddCart(private val product: Product) : DialogFragment() {

    private var _binding:DialogConfirmAddCartBinding? = null

    @Inject
    lateinit var dbServices: PharmacyDAO

    private val scope = CoroutineScope(Dispatchers.IO)


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_MyPharmacy_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = DialogConfirmAddCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.isCancelable = false


        setDataToView(product = product)

        var quantity: Int = binding.edtQuantity.text.toString().toInt()

        binding.ibIncrement.setOnClickListener {
            quantity++
            binding.edtQuantity.setText(quantity.toString())
        }

        binding.ibDecrement.setOnClickListener {
            if (quantity > 1) quantity--
            binding.edtQuantity.setText(quantity.toString())
        }

        binding.btnConfirm.setOnClickListener {
            val data: CartEntity? = extractData()
            if (data != null) {
                saveToCache(data)
            }
        }

        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

    }

    private fun extractData(): CartEntity? {

        if (binding.edtQuantity.text.isNullOrEmpty()) {
            binding.edtQuantity.error = "Invalid Input"
            binding.edtQuantity.requestFocus()
            return null
        }

        val quantityStr = binding.edtQuantity.text.toString()
        val quantity: Int = quantityStr.toInt()

        if (quantity == 0) {
            binding.edtQuantity.error = "Invalid Input"
            binding.edtQuantity.requestFocus()
            return null
        }

        return CartEntity(
            name = product.name,
            productID = product.id,
            slug = product.slug,
            quantity = quantityStr,
            mg = product.mg,
            code = product.code,
            details = product.details,
            price = product.price,
            new_price = product.new_price,
            status = product.status,
            stock = product.stock,
            point = product.point,
            is_featured = product.is_featured,
            productType = product.product_type?.name,
            productTypeId = product.product_type?.id,
            productImage = product.product_images?.get(0)?.file_path
        )
    }

    private fun setDataToView(product: Product) {
        binding.tvMedicineName.text = product.name
        binding.tvMedicineType.text = product.product_type?.name ?: ""
        binding.tvNewPrice.text = product.new_price
        binding.tvOldPrice.text = product.price
    }

    private fun saveToCache(cartEntity: CartEntity) {

        scope.launch {
            dbServices.insertCart(cartEntity)
            val cartProduct = dbServices.getAllCartProduct()

            requireActivity().runOnUiThread {

                dismiss()
            }

        }

    }


    companion object {
        const val TAG = "DialogConfirmAddCart"
    }


}