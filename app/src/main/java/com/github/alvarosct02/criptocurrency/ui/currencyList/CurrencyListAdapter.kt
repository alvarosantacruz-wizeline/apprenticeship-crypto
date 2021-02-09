package com.github.alvarosct02.criptocurrency.ui.currencyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.databinding.ItemListCurrencyBinding

class CurrencyListAdapter(
    private val viewModel: CurrencyListViewModel
) : ListAdapter<Ticker, CurrencyListAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemListCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyListViewModel, ticker: Ticker) {
            binding.viewModel = viewModel
            binding.ticker = ticker
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Ticker>() {
    override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.book == newItem.book
    }

    override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.last == newItem.last
    }
}
