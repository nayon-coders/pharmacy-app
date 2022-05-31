package com.mypharmacybd.ui.main_activity.fragments.category_details.adapter

import android.content.Context
import android.util.Log
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.dialog.DialogConfirmAddCart
import com.mypharmacybd.ui.main_activity.fragments.category_details.CategoryDetailsContract
import com.mypharmacybd.ui.main_activity.fragments.product_details.FragmentProductDetails
import java.text.DecimalFormat


class ListProductAdapter(
    private val view:CategoryDetailsContract.View,
    private val products: Products,
    private val context: Context
) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivCartImage:ImageView = itemView.findViewById(R.id.ivCartImage)
        val tvCartProductName:TextView = itemView.findViewById(R.id.tvCartProductName)
        val tvCartProductType:TextView = itemView.findViewById(R.id.tvCartType)
        val tvBrandName:TextView = itemView.findViewById(R.id.tvBrandName)
        val tvMainPrice:TextView = itemView.findViewById(R.id.tvMainPrice)
        val tvDiscountPrice:TextView = itemView.findViewById(R.id.tvDiscountPrice)
        val tvDiscountTag:TextView = itemView.findViewById(R.id.tvDiscountTag)
        val addToCart:View = itemView.findViewById(R.id.addCart)
       // val viewProduct:View = itemView.findViewById(R.id.viewProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_product_in_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = products.data[position]
        setDataToView(holder, product)

        holder.addToCart.setOnClickListener {
            view.onAddToCartClicked(product)
        }

//        holder.addToCart.setOnClickListener {
//            Log.e(FragmentProductDetails.TAG, "onViewCreated: is called")
//            //Show Add to cart Confirmation Dialog
//            DialogConfirmAddCart(product).show(
//                requireActivity().supportFragmentManager,
//                DialogConfirmAddCart.TAG
//            )
//        }




    }

    fun setDataToView(holder:ViewHolder, product: Product){

        val imageUrl:String = if(product.product_images.isNullOrEmpty()){
            ""
        } else {
            Constants.WEB_BASE_URL + (product.product_images?.get(0)?.file_path ?: "")
        }

        Glide.with(context)
            .load(imageUrl)
            .into(holder.ivCartImage)

        holder.tvCartProductName.text = product.name

        if(product.product_type == null){
            holder.tvCartProductType.visibility = View.GONE
        } else {
            holder.tvCartProductType.text = product.product_type?.name ?: ""
        }

        val discountPrice = context.getString(R.string.currency_bdt) + product.new_price
        holder.tvDiscountPrice.text = discountPrice



        val price = context.getString(R.string.currency_bdt) + product.price
        holder.tvMainPrice.text = price

        //Show Product discount Presents
        var OldPrice = (product.price)?.toDouble()
        var NewPricet = (product.new_price)?.toDouble()
        var DiscountPrice = (OldPrice!! - NewPricet!!)
        var DiscountPersentis = (OldPrice!! / 100!! ) / DiscountPrice
        val dec = DecimalFormat("#,###.##")
        if(DiscountPrice > 0){
            holder.tvDiscountTag.text = "${dec.format(DiscountPersentis)} % OFF".toString()
        }else{
            holder.tvDiscountTag.setBackgroundColor(0xFFffff)
        }


        if(product.brand != null){
            holder.tvBrandName.text = product.brand!!.name
        } else holder.tvBrandName.visibility = View.GONE



    }

    override fun getItemCount() = products.data.size

}