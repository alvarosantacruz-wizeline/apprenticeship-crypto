package com.github.alvarosct02.criptocurrency.features.currencyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.VerticalSpaceItemDecoration
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
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()

        binding.btDetail.setOnClickListener {
            val navDirections =
                CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment()
            findNavController().navigate(navDirections)
        }

        viewModel.getAvailableBooks()

    }

    private fun setupListAdapter() {
        val listAdapter = CurrencyListAdapter(viewModel)
        binding.rvCurrencies.adapter = listAdapter
        val spaceSize = resources.getDimensionPixelSize(R.dimen.size_default)
        binding.rvCurrencies.addItemDecoration(VerticalSpaceItemDecoration(spaceSize))
    }

}
