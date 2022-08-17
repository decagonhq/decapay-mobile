package com.decagonhq.decapay.feature.budgetdetails.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R



class LineItemAdaptor(var list: MutableList<Int>, var clicker: LineItemClicker) :RecyclerView.Adapter<LineItemAdaptor.LineItemViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.budget_line_item, parent, false)
        return LineItemAdaptor.LineItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LineItemViewHolder, position: Int) {
        val currentBudget = list[position]
        holder.initialize(currentBudget, clicker)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setLineItems() {
        notifyDataSetChanged()
    }

    fun deleteItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size)
    }

    class LineItemViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) {
        private var button: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_line_item_elipsis_ib)

        fun initialize(currentCategoryItem: Int, clicker: LineItemClicker) {

            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentCategoryItem, adapterPosition, button)
            }

        }
    }
}