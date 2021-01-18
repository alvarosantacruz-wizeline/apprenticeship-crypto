package com.github.alvarosct02.criptocurrency.features.currencyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.databinding.ItemListCurrencyBinding

class CurrencyListAdapter(
    private val viewModel: CurrencyListViewModel
) : ListAdapter<Book, CurrencyListAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemListCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyListViewModel, book: Book) {
            binding.viewModel = viewModel
            binding.book = book
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

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.book == newItem.book
    }
}
