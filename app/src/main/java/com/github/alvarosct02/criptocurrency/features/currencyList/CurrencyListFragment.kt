package com.github.alvarosct02.criptocurrency.features.currencyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.alvarosct02.criptocurrency.EventObserver
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.VerticalSpaceItemDecoration
import com.github.alvarosct02.criptocurrency.databinding.FragmentCurrencyDetailBinding
import com.github.alvarosct02.criptocurrency.databinding.FragmentCurrencyListBinding
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment

class CurrencyListFragment : BaseFragment() {
    private val viewModel by viewModels<CurrencyListViewModel>()
    private lateinit var binding: FragmentCurrencyListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupListAdapter()

        viewModel.getAvailableBooks()
    }

    private fun setupNavigation() {
        viewModel.openBookEvent.observe(viewLifecycleOwner, EventObserver {
            openBookEvent(it.book)
        })
    }

    private fun openBookEvent(bookId: String){
        val navDirections =
            CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment(bookId)
        findNavController().navigate(navDirections)
    }

    private fun setupListAdapter() {
        val listAdapter = CurrencyListAdapter(viewModel)
        binding.rvCurrencies.adapter = listAdapter
        val spaceSize = resources.getDimensionPixelSize(R.dimen.size_default)
        binding.rvCurrencies.addItemDecoration(VerticalSpaceItemDecoration(spaceSize))
    }

}
