package com.mypharmacybd.ui.main_activity.fragments.search.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.main_activity.fragments.search.SearchContract

class SearchResultAdapter(private val listProduct: List<Product>,
                          private val view:SearchContract.View,
                          private val context:Context) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_product_in_row, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProduct[position]
        setDataToView(holder = holder, product = product)

        holder.addToCart.setOnClickListener {
            view.onAddCartClicked(product)
        }
    }

    override fun getItemCount(): Int = listProduct.size

    fun setDataToView(holder: ViewHolder, product: Product){
        val imageUrl:String = Constants.WEB_BASE_URL + (product.product_images?.get(0)?.file_path ?: "")

        Glide.with(context)
            .load(imageUrl)
            .into(holder.ivCartImage)

        holder.tvCartProductName.text = product.name

        if(product.product_type == null){
            holder.tvCartProductType.visibility = View.GONE
        } else {
            holder.tvCartProductType.text = product.product_type?.name ?: ""
        }

        val discountPrice = context.getString(R.string.currency_bdt) + product.price
        holder.tvDiscountPrice.text = discountPrice

        val price = context.getString(R.string.currency_bdt) + product.price
        holder.tvMainPrice.text = price

        if(product.brand != null){
            holder.tvBrandName.text = product.brand!!.name
        } else holder.tvBrandName.visibility = View.GONE

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCartImage: ImageView = itemView.findViewById(R.id.ivCartImage)
        val tvCartProductName: TextView = itemView.findViewById(R.id.tvCartProductName)
        val tvCartProductType: TextView = itemView.findViewById(R.id.tvCartType)
        val tvBrandName: TextView = itemView.findViewById(R.id.tvBrandName)
        val tvMainPrice: TextView = itemView.findViewById(R.id.tvMainPrice)
        val tvDiscountPrice: TextView = itemView.findViewById(R.id.tvDiscountPrice)
        val tvDiscountTag: TextView = itemView.findViewById(R.id.tvDiscountTag)
        val addToCart:View = itemView.findViewById(R.id.addCart)
    }
}