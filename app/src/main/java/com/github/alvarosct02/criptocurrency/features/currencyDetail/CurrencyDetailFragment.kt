package com.github.alvarosct02.criptocurrency.features.currencyDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class CurrencyDetailFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBookDetail("btc_mxn")
    }

//    TODO: Pending Refactor with LiveData
//    TODO: Pending Refactor with Coroutines
//    TODO: Pending Refactor with ServiceLocator
//    TODO: Pending Refactor into a ViewModel
    private fun getBookDetail(book:String) {

        RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
            .create<CurrenciesService>().getBookDetail(book).enqueue(
                object : Callback<BitsoWrapper<Ticker>> {
                    override fun onResponse(
                        call: Call<BitsoWrapper<Ticker>>,
                        response: Response<BitsoWrapper<Ticker>>
                    ) {
                        val message = "Book volumen: ${response.body()?.payload?.volume}"
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                        Log.e("ASCT", message)
                    }

                    override fun onFailure(call: Call<BitsoWrapper<Ticker>>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }


}
