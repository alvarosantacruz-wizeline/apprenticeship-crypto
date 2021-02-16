package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.github.alvarosct02.criptocurrency.BindingUtils
import com.github.alvarosct02.criptocurrency.Constants.REFRESH_TIME_IN_MILLIS
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.VerticalSpaceItemDecoration
import com.github.alvarosct02.criptocurrency.databinding.FragmentCurrencyDetailBinding
import com.github.alvarosct02.criptocurrency.setTickerDataInteractive
import com.github.alvarosct02.criptocurrency.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CurrencyDetailFragment : BaseFragment() {

    private val args: CurrencyDetailFragmentArgs by navArgs()
    private val viewModel: CurrencyDetailViewModel by viewModels()
    private lateinit var binding: FragmentCurrencyDetailBinding

    init {
        lifecycleScope.launchWhenStarted {
            while (true) {
                delay(REFRESH_TIME_IN_MILLIS)
                viewModel.refreshTicker()
            }
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListAdapters()

        viewModel.ticker.observe(binding.lifecycleOwner!!) { uiState ->
            Log.e("ASCT ticker", uiState.isLoading.toString())
        }

        viewModel.tickerHistory.observe(binding.lifecycleOwner!!) { uiState ->
            val ticker = viewModel.ticker.value?.data ?: return@observe
            val color = BindingUtils.colorFromNumber(ticker.dayChangePercentage)
            binding.chart.isVisible = true
            binding.chart.setTickerDataInteractive(uiState, color)
        }

        val bookId = args.bookId
        viewModel.setBook(bookId)
    }

    private fun setupListAdapters() {
        val spaceSize = resources.getDimensionPixelSize(R.dimen.size_small)
        binding.rvTrades.adapter = OrderListAdapter(viewModel)
        binding.rvTrades.addItemDecoration(VerticalSpaceItemDecoration(spaceSize))
    }
}
