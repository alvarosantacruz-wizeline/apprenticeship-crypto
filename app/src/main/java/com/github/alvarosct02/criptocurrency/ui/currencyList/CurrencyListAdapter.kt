package com.github.alvarosct02.criptocurrency.ui.currencyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.BindingUtils
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.databinding.ItemListCurrencyBinding
import com.github.alvarosct02.criptocurrency.setTickerData

class CurrencyListAdapter(
    private val viewModel: CurrencyListViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : ListAdapter<Ticker, CurrencyListAdapter.ViewHolder>(BookDiffCallback()) {

    override fun submitList(list: MutableList<Ticker>?) {
        list?.sortWith(compareByDescending<Ticker> { it.to }.thenBy { it.from })
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewLifecycleOwner)
    }

    class ViewHolder(private val binding: ItemListCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyListViewModel, ticker: Ticker) {
            binding.viewModel = viewModel
            binding.ticker = ticker

            viewModel.tickerHistoryByBook(ticker.book)
                .observe(binding.lifecycleOwner!!) { uiState ->
                    val color = BindingUtils.colorFromNumber(ticker.dayChangePercentage)
                    binding.chart?.setTickerData(uiState, color)
                }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, viewLifecycleOwner: LifecycleOwner): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListCurrencyBinding.inflate(layoutInflater, parent, false)
                binding.lifecycleOwner = viewLifecycleOwner
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
