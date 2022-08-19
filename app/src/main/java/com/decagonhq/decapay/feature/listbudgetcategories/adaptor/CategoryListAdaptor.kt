package com.decagonhq.decapay.feature.listbudgetcategories.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.Data
import kotlin.random.Random

class CategoryListAdaptor(var list: MutableList<Data>, var clicker: CategoryClicker) : RecyclerView.Adapter<CategoryListAdaptor.CategoryViewHolder>() {

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
        private var textView:TextView = itemView.findViewById<TextView>(R.id.category_list_item_title_tv);

        fun initialize(currentCategoryItem: Data, clicker: CategoryClicker) {
            val list = listOf<Int>(R.color.light_green,R.color.light_yellow,R.color.light_blue,R.color.light_red)

            itemView.setBackgroundResource(list[getBackgroundColor(adapterPosition)])

            textView.text = currentCategoryItem.title

            button.setOnClickListener {
                clicker.onClickItemEllipsis(currentCategoryItem, adapterPosition, button)
            }




        }

        private fun getBackgroundColor(position: Int):Int{
            return when(0){
                position%4 -> 3
                position%3-> 2
                position%2 -> 1
                else -> 0
            }


        }
    }
}
