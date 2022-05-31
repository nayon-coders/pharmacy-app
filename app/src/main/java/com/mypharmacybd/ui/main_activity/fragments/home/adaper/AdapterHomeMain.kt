package com.mypharmacybd.ui.main_activity.fragments.home.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.ui.main_activity.fragments.home.HomeContract

class AdapterHomeMain(
    private val context: Context,
    private val view: HomeContract.View,
    private val categories: Categories
) : RecyclerView.Adapter<AdapterHomeMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_main_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category:Category = categories.data[position]

        if(category.products == null
            || category.products?.data?.isEmpty() == true) {
                holder.itemView.visibility = View.GONE
                return
        }

        holder.tvCategoryName.text = category.name
        holder.rvCategory.adapter =
            category.products?.let { AdapterGridProduct(context, view, it) }

        holder.tvSeeAll.setOnClickListener{
            view.onViewDetailsClicked(category)
        }

    }

    override fun getItemCount(): Int = categories.data.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainCardLayout:CardView = itemView.findViewById(R.id.mainCardLayout);
        var tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val tvSeeAll: TextView = itemView.findViewById(R.id.tvSeeAll)
        val rvCategory: RecyclerView = itemView.findViewById(R.id.rvCategory)
    }
}