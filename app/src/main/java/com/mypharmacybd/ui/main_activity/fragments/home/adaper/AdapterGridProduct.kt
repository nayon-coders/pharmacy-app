package com.mypharmacybd.ui.main_activity.fragments.home.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.ProductImages
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.other.Common.getLoadingDrawable
import com.mypharmacybd.other.Constants.WEB_BASE_URL
import com.mypharmacybd.ui.main_activity.fragments.home.HomeContract
import java.text.DecimalFormat

class AdapterGridProduct(
    private val context: Context,
    private val view: HomeContract.View,
    private val products: Products,
) : RecyclerView.Adapter<AdapterGridProduct.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvMedicineName)
        val tvProductType: TextView = itemView.findViewById(R.id.tvProductType)
        val tvDiscountTag: TextView = itemView.findViewById(R.id.tvDiscountTag)
        val tvMainPrice: TextView = itemView.findViewById(R.id.tvMainPrice)
        val tvDiscountPrice: TextView = itemView.findViewById(R.id.tvDiscountPrice)
        val tvDiscountPercentage: TextView = itemView.findViewById(R.id.tvDiscountPercentage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_product_cart,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var url = ""
        val product: Product = products.data[position]
        //print(products.data[position])


        val productImages: List<ProductImages>? = products.data[position].product_images
        if (!productImages.isNullOrEmpty()) {
            url = WEB_BASE_URL +
                    products.data[position].product_images?.get(0)?.file_path
        }

        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(getLoadingDrawable(context, centerRadius = 10f, strokeWidth = 2f))
            .centerInside()
            .into(holder.ivProduct)

        holder.tvProductName.text = product.name
        val discountPrice = "\u09F3 ${product.price}"
        val newPrice = "\u09F3 ${product.new_price}"
        holder.tvMainPrice.text = discountPrice
        holder.tvDiscountPrice.text = newPrice
        holder.tvDiscountTag.text = product.status

        //Show Product discount Presents
        var OldPrice = (product.price)?.toDouble()
        var NewPricet = (product.new_price)?.toDouble()
        var DiscountPrice = (OldPrice!! - NewPricet!!)
        var DiscountPersentis = (OldPrice!! / 100!! ) / DiscountPrice
        val dec = DecimalFormat("#,###.##")
        if(DiscountPrice > 0){
            holder.tvDiscountPercentage.text = "${dec.format(DiscountPersentis)} % OFF".toString()
        }else{
            holder.tvDiscountPercentage.setBackgroundColor(0xFFffff)
        }

        if (product.product_type != null) {
            holder.tvProductType.visibility = VISIBLE
        }

        holder.tvProductType.text = product.product_type?.name ?: ""

        holder.itemView.setOnClickListener {
            view.viewProductDetails(product)
        }
    }

    override fun getItemCount() = products.data.size



    companion object {
        private const val TAG = "AdapterGridProduct"
    }


}
