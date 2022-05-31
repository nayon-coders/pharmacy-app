package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypharmacybd.R
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact.PrescriptionListContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription

class PrescriptionListMedicineAdapter (
    private val view: PrescriptionListContact.View,
    private val context: Context,
    private var ordersResponse: AllPrescription
) : RecyclerView.Adapter<PrescriptionListMedicineAdapter
.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_orders, parent,false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val orderData = getObject().data?.get(position)
        Log.e("Dim"," orderData.orderNumber :"+ orderData?.rqCode)
        holder.tvCategoryName.text = "${orderData?.rqCode}"
        holder.tvStatus.text = "${orderData?.status}"

        if(holder.tvStatus.text.toString() == "Confirmed"){
            holder.tvStatus.setTextColor(Color.parseColor("#008F20"))
        }
        else if(holder.tvStatus.text.toString() == "Canceled"){
            holder.tvStatus.setTextColor(Color.parseColor("#D83737"))
        }
        else{
            holder.tvStatus.setTextColor(Color.parseColor("#1da1f2"))
        }
        holder.tvCreateDate.text = "Order Date: ${orderData?.deliveryMan?.createdAt}"
        holder.itemView.setOnClickListener { view.onPrescriptionItemClicked(orderData?.id!!)}
    }

    fun setObjectData(orders: AllPrescription){ordersResponse = orders}
    fun getObject() : AllPrescription{
        return ordersResponse
    }

    override fun getItemCount(): Int  = ordersResponse.data!!.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryName: TextView = itemView.findViewById(R.id.tvOrderNumber)
        var tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        var tvCreateDate: TextView = itemView.findViewById(R.id.tvCreateDate)
    }
}