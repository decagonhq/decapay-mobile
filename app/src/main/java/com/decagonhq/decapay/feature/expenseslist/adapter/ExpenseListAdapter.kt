package com.decagonhq.decapay.feature.expenseslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent

class ExpenseListAdapter(var list: MutableList<ExpenseContent>, var clicker: ExpenseClicker) :
    RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
        val currentExpense = list[position]
        holder.initialize(currentExpense, clicker)
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

    class ExpenseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var button: ImageButton =
            itemView.findViewById<ImageButton>(R.id.expense_item_ellipsis_ib)

        private var amount: TextView =
            itemView.findViewById<TextView>(R.id.expense_item_amount_tv)

        private var description: TextView =
            itemView.findViewById<TextView>(R.id.expense_item_description_tv)
        private var date: TextView =
            itemView.findViewById<TextView>(R.id.expense_item_time_tv)

        fun initialize(currentExpenseItem: ExpenseContent, clicker: ExpenseClicker) {

            amount.text = currentExpenseItem.displayAmount
            description.text = currentExpenseItem.description
            date.text = currentExpenseItem.displayTransactionDate

            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentExpenseItem, adapterPosition,button)
            }


        }
    }
}
