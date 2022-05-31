package com.mypharmacybd.ui.main_activity.fragments.categories.adapter

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
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract

class OrdersAdapter(
    private val view:OrderListContract.View,
    private val context: Context,
    private var ordersResponse: GetOrderResponse
    ) : RecyclerView.Adapter<OrdersAdapter
.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_orders, parent,false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderData = getObject().data[position]
        Log.e("Dim"," orderData.orderNumber :"+ orderData.orderNumber)
        holder.tvCategoryName.text = "${orderData.orderNumber}"
        holder.tvStatus.text = "${orderData.status}"
        if(holder.tvStatus.text.toString() == "Canceled"){
            holder.tvStatus.setTextColor(Color.parseColor("#D83737"))
        }
        else if(holder.tvStatus.text.toString() == "Delivered"){
            holder.tvStatus.setTextColor(Color.parseColor("#008F20"))
        }
        else if(holder.tvStatus.text.toString() == "Processing"){
            holder.tvStatus.setTextColor(Color.parseColor("#FF03DAC5"))
        }
        else{
            holder.tvStatus.setTextColor(Color.parseColor("#1da1f2"))
        }
        holder.tvCreateDate.text = "Order Date: ${orderData.date}"
        holder.itemView.setOnClickListener { view.onCategoryClicked(orderData)}
    }

    override fun getItemCount(): Int  = ordersResponse.data.size
    fun getObject(): GetOrderResponse{
        return ordersResponse
    }

    fun setObject(orders : GetOrderResponse){
        ordersResponse = orders
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryName:TextView = itemView.findViewById(R.id.tvOrderNumber)
        var tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
        var tvCreateDate:TextView = itemView.findViewById(R.id.tvCreateDate)
    }
}