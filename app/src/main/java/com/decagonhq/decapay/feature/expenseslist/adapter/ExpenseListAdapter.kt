package com.decagonhq.decapay.feature.expenseslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R

class ExpenseListAdapter(var list: MutableList<Int>, var clicker: ExpenseClicker) :
    RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
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

    class ExpenseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var button: ImageButton =
            itemView.findViewById<ImageButton>(R.id.expense_item_ellipsis_ib)

        fun initialize(currentExpenseItem: Int, clicker: ExpenseClicker) {

            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentExpenseItem, adapterPosition,button)
            }


        }
    }
}
