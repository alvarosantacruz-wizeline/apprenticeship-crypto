package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.databinding.ItemListTradeBinding

class OrderListAdapter(
    private val viewModel: CurrencyDetailViewModel
) : ListAdapter<Trade, OrderListAdapter.ViewHolder>(BookOrderDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemListTradeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyDetailViewModel, trade: Trade) {
            binding.viewModel = viewModel
            binding.trade = trade
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListTradeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BookOrderDiffCallback : DiffUtil.ItemCallback<Trade>() {
    override fun areItemsTheSame(oldItem: Trade, newItem: Trade): Boolean {
        return oldItem.tid == newItem.tid
    }

    override fun areContentsTheSame(oldItem: Trade, newItem: Trade): Boolean {
        return oldItem.toString() == newItem.toString()
    }
}
