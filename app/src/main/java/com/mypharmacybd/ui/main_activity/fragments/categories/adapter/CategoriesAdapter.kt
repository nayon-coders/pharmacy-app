package com.mypharmacybd.ui.main_activity.fragments.categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract

class CategoriesAdapter(
    private val view:CategoriesContract.View,
    private val context: Context,
    private val categories: Categories
    ) : RecyclerView.Adapter<CategoriesAdapter
.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_categories, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories.data[position]
        holder.tvCategoryName.text = category.name
        holder.tvCategoryName.setOnClickListener { view.onCategoryClicked(category)}
    }

    override fun getItemCount(): Int  = categories.data.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryName:TextView = itemView.findViewById(R.id.tvCategoryName)
    }
}