package com.decagonhq.decapay.feature.listbudget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R

class BudgetListAdapter(var list: MutableList<Int>, var clicker: BudgetClicker) :
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
        //this.list = results
        notifyDataSetChanged()
    }

    fun deleteItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size)
    }


    class BudgetListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var button: ImageButton = itemView.findViewById<ImageButton>(R.id.budget_list_item_elipsis_ib)


        fun initialize(currentBudgetItem: Int, clicker: BudgetClicker) {

            itemView.setOnClickListener {
                clicker.onClickItem(currentBudgetItem, adapterPosition)
            }

            button.setOnClickListener {
                clicker.onClickItemElipsis(currentBudgetItem, adapterPosition, button)
            }
        }
    }
}