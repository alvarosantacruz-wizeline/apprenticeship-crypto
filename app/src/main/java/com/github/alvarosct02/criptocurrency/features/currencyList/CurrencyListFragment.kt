package com.github.alvarosct02.criptocurrency.features.currencyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment

class CurrencyListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bt_detail).setOnClickListener {
            val navDirections = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment()
            findNavController().navigate(navDirections)
        }
    }
}
