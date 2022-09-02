package com.decagonhq.decapay.feature.listbudget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.model.Content

class BudgetListAdapter(var list: MutableList<Content>, var clicker: BudgetClicker, val context: Context) :
    RecyclerView.Adapter<BudgetListAdapter.BudgetListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.budget_item, parent, false)
        return BudgetListViewHolder(view,context)
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

    fun clearList(){
        list.clear()
        notifyDataSetChanged()
    }

    fun deleteItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size)
    }

    class BudgetListViewHolder(itemView: View,val context: Context) : RecyclerView.ViewHolder(itemView) {
        private var button: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_list_item_elipsis_ib)
        private var titleTextView: TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_title_tv)
        private var amountTextView: TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_amount_tv)
        private var spentTextView: TextView = itemView.findViewById<TextView>(R.id.budget_list_item_amount_spent_tv)
        private var percentageTextView: TextView = itemView.findViewById<TextView>(R.id.budget_list_item_budget_percentage_spent_tv)

        fun initialize(currentBudgetItem: Content, clicker: BudgetClicker) {
            amountTextView.text = currentBudgetItem.displayProjectedAmount
            percentageTextView.text = currentBudgetItem.displayPercentageSpentSoFar
            spentTextView.text = currentBudgetItem.displayTotalAmountSpentSoFar
            titleTextView.text = currentBudgetItem.title

            if(currentBudgetItem.projectedAmount>100){
                percentageTextView.setTextColor( AppCompatResources.getColorStateList(context, R.color.red))
                spentTextView.setTextColor( AppCompatResources.getColorStateList(context, R.color.red))
            }

            itemView.setOnClickListener {
                clicker.onClickItem(currentBudgetItem, adapterPosition)
            }
            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentBudgetItem, adapterPosition, button)
            }
        }
    }
}
