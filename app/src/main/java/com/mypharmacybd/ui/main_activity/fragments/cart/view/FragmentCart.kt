package com.mypharmacybd.ui.main_activity.fragments.cart.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentCartBinding
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.ui.auth.ActivityAuth
import com.mypharmacybd.ui.main_activity.fragments.cart.CartContract
import com.mypharmacybd.ui.main_activity.fragments.cart.adapter.CartAdapter
import com.mypharmacybd.user.Cart
import com.mypharmacybd.user.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCart : Fragment(), CartContract.View {
    private var _binding: FragmentCartBinding? = null

    @Inject
    lateinit var presenter: CartContract.Presenter


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
        presenter.triggerCartProduct()

        binding.cartTopBar.tvTitle.text = getString(R.string.cart)

        if (Cart.cartListLiveData != null) {
            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
                binding.cartTopBar.cart.tvCartCounter.text = it.size.toString()
            }
        }

        binding.cartTopBar.ivBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnBuyMore.setOnClickListener {
            val action = FragmentCartDirections.actionFragmentCartToFragmentHome()
            findNavController().navigate(action)
        }

        binding.btnCheckout.setOnClickListener {
            if (Session.loginStatus) {
                val action = FragmentCartDirections.actionFragmentCartToFragmentCheckout()
                findNavController().navigate(action)
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Login Required!")
                    .setMessage("You should login to continue checkout.")
                    .setPositiveButton("Login") { _, _ ->
                        val action = FragmentCartDirections
                            .actionFragmentCartToActivityAuth(ActivityAuth.AUTH_LOGIN)
                        findNavController().navigate(action)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }
    }

    private fun calculateSubTotal(cartEntityList: List<CartEntity>): Double {
        var total = 0.0
        for (cart in cartEntityList) {
            var price: Double = cart.new_price?.toDouble() ?: 0.0
            price *= cart.quantity?.toInt() ?: 1
            Log.d(TAG, "calculateSubTotal: Price = $price")
            Log.d(TAG, "calculateSubTotal: total price = $total")
            total += price
        }

        return total
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentCart()

        private const val TAG = "FragmentCart"
    }


    override fun setDataToView(allCartEntity: LiveData<List<CartEntity>>) {
        allCartEntity.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                hideCartView()
            } else {
                binding.rvCart.adapter = CartAdapter(this, requireContext(), it)

                val price = calculateSubTotal(it)
                val shippingCharge = 45.0
                val subTotal = getString(R.string.currency_bdt) + price
                val grandTotal = getString(R.string.currency_bdt) + (price + shippingCharge)

                binding.tvSubTotal.text = subTotal
                binding.tvGrandTotal.text = grandTotal
            }
        }
    }


    override fun onCartUpdate(cartEntity: CartEntity, holder: CartAdapter.ViewHolder) {
        holder.tvUpdateCart.text = getString(R.string.saved)

        presenter.updateCartItem(cartEntity, holder)

        val animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.text_visible_fade
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                holder.tvUpdateCart.visibility = GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        holder.tvUpdateCart.animation = animation
    }

    override fun visibleCartView() {
        requireActivity().runOnUiThread {
            binding.cartNestedScrollView.visibility = VISIBLE
            binding.cartCheckoutLayout.visibility = VISIBLE
        }

    }

    override fun hideCartView() {
        requireActivity().runOnUiThread {
            binding.cartNestedScrollView.visibility = GONE
            binding.cartCheckoutLayout.visibility = GONE
            binding.tvCartEmptyMessage.visibility = VISIBLE
        }
    }

    override fun onRemoveItemClicked(item: CartEntity) {
        presenter.removeCartItem(item)
    }
}