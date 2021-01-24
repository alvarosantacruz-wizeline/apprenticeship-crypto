package com.github.alvarosct02.criptocurrency.features.currencyDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.VerticalSpaceItemDecoration
import com.github.alvarosct02.criptocurrency.databinding.FragmentCurrencyDetailBinding
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment
import com.github.alvarosct02.criptocurrency.features.currencyList.CurrencyListAdapter
import com.github.alvarosct02.criptocurrency.features.currencyList.CurrencyListViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class CurrencyDetailFragment : BaseFragment() {

    private val args: CurrencyDetailFragmentArgs by navArgs()
    private val viewModel: CurrencyDetailViewModel by viewModel()
    private lateinit var binding: FragmentCurrencyDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListAdapters()

        val bookId = args.bookId
        viewModel.getBookOrders(bookId)
        viewModel.getBookTicker(bookId)
    }



    private fun setupListAdapters() {
        val spaceSize = resources.getDimensionPixelSize(R.dimen.size_default)

        binding.rvAsks.adapter = OrderListAdapter(viewModel)
        binding.rvAsks.addItemDecoration(VerticalSpaceItemDecoration(spaceSize))

        binding.rvBids.adapter = OrderListAdapter(viewModel)
        binding.rvBids.addItemDecoration(VerticalSpaceItemDecoration(spaceSize))
    }

}
