package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mypharmacybd.R
import com.mypharmacybd.other.Common
import com.mypharmacybd.other.Constants.WEB_BASE_URL
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module.OrderListItemSupport
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact.PrescriptionListContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription
import java.text.DecimalFormat

class PrescriptionListAdapter (
    private val view: OrderListItemSupport.View,
    private val context: Context,
    private val response: PrescriptionAllMedicne
) : RecyclerView.Adapter<PrescriptionListAdapter
.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_display_medicine, parent,false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  medicine = response.data?.requestedProducts!![position].product
        holder.tvName.text = medicine!!.name
        holder.tvPrice.text = medicine.new_price + " Tk"

        //Show Product discount Presents
        var OldPrice = (medicine.price)?.toDouble()
        var NewPricet = (medicine.new_price)?.toDouble()
        var DiscountPrice = (OldPrice!! - NewPricet!!)
        var DiscountPersentis = (OldPrice!! / 100!! ) / DiscountPrice
        val dec = DecimalFormat("#,###.##")

        if(DiscountPrice > 0){
            dec.format(DiscountPersentis)
            holder.tvDiscountPercentage.text = "${dec.format(DiscountPersentis)} % OFF".toString()
        }else{
            holder.tvDiscountPercentage.setBackgroundColor(0xFFffff)
        }

        holder.brandMedicine.text = medicine.brand?.name

        if(medicine.stock.isNullOrEmpty()){
            holder.tvStatus.text = "Stock Out"
        }
        holder.tvBuy.setOnClickListener {
            view.orderBuyClick(medicine)
        }

        Glide.with(context)
            .load(WEB_BASE_URL + medicine.product_images?.get(0)?.file_path)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(Common.getLoadingDrawable(context, centerRadius = 10f, strokeWidth = 2f))
            .centerInside()
            .into(holder.imageViewMedicine)

    }

    override fun getItemCount(): Int  = response.data?.requestedProducts!!.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.nameMedicine)
        var tvPrice: TextView = itemView.findViewById(R.id.priceMedicine)
        var tvStatus: TextView = itemView.findViewById(R.id.inStock)
        var tvBuy : TextView = itemView.findViewById(R.id.buy)
        var imageViewMedicine : ImageView = itemView.findViewById(R.id.imageViewMedicine)
        var tvDiscountPercentage: TextView = itemView.findViewById(R.id.tvDiscountPercentage)
        var brandMedicine : TextView = itemView.findViewById(R.id.brandMedicine)
    }
}