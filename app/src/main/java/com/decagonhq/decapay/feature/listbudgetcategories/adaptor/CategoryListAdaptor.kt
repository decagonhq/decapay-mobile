package com.decagonhq.decapay.feature.listbudgetcategories.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R

class CategoryListAdaptor(var list: MutableList<Int>, var clicker: CategoryClicker) : RecyclerView.Adapter<CategoryListAdaptor.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryListAdaptor.CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentBudget = list[position]
        holder.initialize(currentBudget, clicker)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCategory() {
        notifyDataSetChanged()
    }

    fun deleteItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var button: ImageButton = itemView.findViewById<ImageButton>(R.id.category_list_item_elipsis_ib)

        fun initialize(currentCategoryItem: Int, clicker: CategoryClicker) {

            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentCategoryItem, adapterPosition, button)
            }
        }
    }
}
