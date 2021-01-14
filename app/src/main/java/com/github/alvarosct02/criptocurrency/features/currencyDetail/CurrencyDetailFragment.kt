package com.github.alvarosct02.criptocurrency.features.currencyDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment


class CurrencyDetailFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_detail, container, false)
    }


}
