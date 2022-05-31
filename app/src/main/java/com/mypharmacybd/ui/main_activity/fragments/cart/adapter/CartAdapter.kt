package com.mypharmacybd.ui.main_activity.fragments.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mypharmacybd.R
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.other.Common.getLoadingDrawable
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.main_activity.fragments.cart.CartContract
import java.text.DecimalFormat

class CartAdapter(
    private val mView:CartContract.View,
    private val context: Context,
    private val cartEntityList: List<CartEntity>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartEntity: CartEntity = cartEntityList[position]

        setDataToRow(cartEntity, holder)

        holder.ibIncrement.setOnClickListener {
            var quantity: Int = cartEntity.quantity?.toInt() ?: 1
            quantity++
            holder.edtQuantity.setText(quantity.toString())

            holder.edtQuantity.requestFocus()
        }

        holder.ibDecrement.setOnClickListener {
            var quantity: Int = cartEntity.quantity?.toInt() ?: 1
            if (quantity > 1) {
                quantity--
            }
            holder.edtQuantity.setText(quantity.toString())
        }
        holder.ivRemoveItem.setOnClickListener{
            mView.onRemoveItemClicked(cartEntity)
        }


    }

    private fun setDataToRow(cartEntity: CartEntity, holder: ViewHolder) {
        holder.tvCartProductName.text = cartEntity.name
        holder.tvCartType.text = cartEntity.productType
        holder.tvCartPrice.text = cartEntity.new_price
        holder.edtQuantity.setText(cartEntity.quantity ?: "1")
        holder.tvCartOldPrice.text = cartEntity.price

        //Show Product discount Presents
        var OldPrice = (cartEntity.price)?.toDouble()
        var NewPricet = (cartEntity.new_price)?.toDouble()
        var DiscountPrice = (OldPrice!! - NewPricet!!)
        var DiscountPersentis = (OldPrice!! / 100!! ) / DiscountPrice
        val dec = DecimalFormat("#,###.##")
        if(DiscountPrice > 0){
            holder.tvDiscountPercentage.text = "${dec.format(DiscountPersentis)} % OFF".toString()
        }else{
            holder.tvDiscountPercentage.setBackgroundColor(0xFFffff)
        }


        val url = Constants.WEB_BASE_URL + cartEntity.productImage

        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(getLoadingDrawable(context, centerRadius = 20f, strokeWidth = 4f))
            .centerInside()
            .into(holder.ivCartImage)

        holder.edtQuantity.doOnTextChanged { text, _, _, _ ->
            run {
                if (text != null) {
                    if (text.isNotEmpty()) {
                        val quantity = holder.edtQuantity.text.toString().toInt()

                        if (quantity > 0) cartEntity.quantity = quantity.toString()
                        else holder.edtQuantity.setText("1")

                        holder.tvUpdateCart.visibility = View.VISIBLE
                    } else {
                        holder.edtQuantity.setText("1")
                    }
                }
            }
        }

        holder.tvUpdateCart.setOnClickListener {
            holder.tvUpdateCart.text = context.resources.getString(R.string.saving)
            mView.onCartUpdate(cartEntity, holder)

        }


    }


    override fun getItemCount() = cartEntityList.size

    companion object {
        private const val TAG = "CartAdapter"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCartProductName: TextView = itemView.findViewById(R.id.tvCartProductName)
        val tvCartType: TextView = itemView.findViewById(R.id.tvCartType)
        val tvCartPrice: TextView = itemView.findViewById(R.id.tvCartPrice)
        val tvCartOldPrice: TextView = itemView.findViewById(R.id.tvCartOldPrice)
        val ivCartImage: ImageView = itemView.findViewById(R.id.ivCartImage)

        val edtQuantity: EditText = itemView.findViewById(R.id.edtQuantity)
        val ibIncrement: ImageButton = itemView.findViewById(R.id.ibIncrement)
        val ibDecrement: ImageButton = itemView.findViewById(R.id.ibDecrement)
        val tvUpdateCart: TextView = itemView.findViewById(R.id.tvUpdateCart)
        val tvDiscountPercentage: TextView = itemView.findViewById(R.id.tvDiscountPercentage)
        val ivRemoveItem: ImageView = itemView.findViewById(R.id.ivRemoveItem)

    }

}