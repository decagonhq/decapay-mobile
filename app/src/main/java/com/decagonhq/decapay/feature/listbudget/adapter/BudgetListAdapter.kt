package com.decagonhq.decapay.feature.listbudget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.model.Content
import com.decagonhq.decapay.common.utils.commaSeparatedString

class BudgetListAdapter(var list: MutableList<Content>, var clicker: BudgetClicker) :
    RecyclerView.Adapter<BudgetListAdapter.BudgetListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.budget_item, parent, false)
        return BudgetListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetListViewHolder, position: Int) {
        val currentBudget = list[position]
        holder.initialize(currentBudget, clicker)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setBudget() {
        notifyDataSetChanged()
    }

    fun deleteItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size)
    }


    class BudgetListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private var button: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_list_item_elipsis_ib)
        private  var titleTextView:TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_title_tv)
        private var amountTextView:TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_amount_tv)
        private  var spentTextView:TextView = itemView.findViewById<TextView>(R.id.budget_list_item_amount_spent_tv)
        private  var percentageTextView:TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_percentage_spent_tv)


        fun initialize(currentBudgetItem: Content, clicker: BudgetClicker) {
            amountTextView.text = "₦${currentBudgetItem.projectedAmount.toDouble().commaSeparatedString()}"
            percentageTextView.text = "${currentBudgetItem.percentageSpentSoFar}%"
            spentTextView.text = "₦${((currentBudgetItem.percentageSpentSoFar/100)*currentBudgetItem.projectedAmount).toDouble().commaSeparatedString()}"
            titleTextView.text = currentBudgetItem.title




            itemView.setOnClickListener {
                clicker.onClickItem(currentBudgetItem, adapterPosition)
            }
            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentBudgetItem, adapterPosition, button)
            }
        }
    }
}