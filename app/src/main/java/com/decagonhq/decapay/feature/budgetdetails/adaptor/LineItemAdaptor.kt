package com.decagonhq.decapay.feature.budgetdetails.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem

class LineItemAdaptor(var list: MutableList<LineItem>, var clicker: LineItemClicker) : RecyclerView.Adapter<LineItemAdaptor.LineItemViewHolder>() {

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

    class LineItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var ellipsisButton: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_line_item_elipsis_ib)
        private var logButton: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_line_item_log_btn)
        private var title: TextView = itemView.findViewById<TextView>(R.id.budget_line_item_title_tv)
        private var projectedAmount: TextView = itemView.findViewById<TextView>(R.id.budget_line_item_projected_amount_tv)
        private var amountSoFar: TextView = itemView.findViewById<TextView>(R.id.budget_line_item_amount_so_far_tv)
        private var percentage: TextView = itemView.findViewById<TextView>(R.id.budget_line_item_percentage_tv)

        fun initialize(currentLineItem: LineItem, clicker: LineItemClicker) {

            title.text = currentLineItem.category
            projectedAmount.text = currentLineItem.displayProjectedAmount
            amountSoFar.text = currentLineItem.displayTotalAmountSpentSoFar
            percentage.text = currentLineItem.displayPercentageSpentSoFar

            ellipsisButton.setOnClickListener {
                clicker.onClickItemEllipsis(currentLineItem, adapterPosition, ellipsisButton)
            }

            logButton.setOnClickListener {
//                clicker.onClickItemEllipsis(currentLineItem, adapterPosition, itemView)
                clicker.onClickItemLog(currentLineItem, adapterPosition, itemView)
            }
        }
    }
}
