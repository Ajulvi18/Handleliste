package com.example.handleliste.listdetail

import com.example.handleliste.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.subList
import com.example.handleliste.databinding.ItemCardBinding

class ListDetailAdapter(private val onClick: (ListItem, View) -> Unit) : ListAdapter<ListItem, ListDetailAdapter.ListDetailViewHolder>(ItemDiffCallback) {

    class ListDetailViewHolder(itemView: View, val onClick: (ListItem, View) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.list_detail_text)
        private var currentItem: ListItem? = null
        private val itemCheckBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init{
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it, itemView)
                }
            }
        }

        fun bind(item: ListItem) {
            currentItem = item

            itemTextView.text = item.name
            itemCheckBox.isChecked = item.completion != 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_detail_card, parent, false)
        return ListDetailViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: ListDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.name == newItem.name
    }
}