package com.example.handleliste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.data.subList

class ItemAdapter(private val onClick: (subList) -> Unit) : ListAdapter<subList, ItemAdapter.ItemViewHolder>(ItemDiffCallback) {

    class ItemViewHolder(itemView: View, val onClick: (subList) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private var currentItem: subList? = null

        init{
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: subList) {
            currentItem = item

            itemTextView.text = item.listname
            progressBar.progress = item.progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<subList>() {
    override fun areItemsTheSame(oldItem: subList, newItem: subList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: subList, newItem: subList): Boolean {
        return oldItem.listname == newItem.listname
    }
}