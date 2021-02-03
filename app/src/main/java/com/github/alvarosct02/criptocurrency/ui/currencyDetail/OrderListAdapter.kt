package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.data.models.BookOrder
import com.github.alvarosct02.criptocurrency.databinding.ItemListOrderBinding

class OrderListAdapter(
    private val viewModel: CurrencyDetailViewModel
) : ListAdapter<BookOrder, OrderListAdapter.ViewHolder>(BookOrderDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemListOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyDetailViewModel, bookOrder: BookOrder) {
            binding.viewModel = viewModel
            binding.bookOrder = bookOrder
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListOrderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BookOrderDiffCallback : DiffUtil.ItemCallback<BookOrder>() {
    override fun areItemsTheSame(oldItem: BookOrder, newItem: BookOrder): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BookOrder, newItem: BookOrder): Boolean {
        return oldItem.toString() == newItem.toString()
    }
}
