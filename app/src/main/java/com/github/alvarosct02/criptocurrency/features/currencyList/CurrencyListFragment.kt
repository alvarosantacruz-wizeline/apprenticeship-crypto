package com.github.alvarosct02.criptocurrency.features.currencyList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.R
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import com.github.alvarosct02.criptocurrency.features.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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
            val navDirections =
                CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment()
            findNavController().navigate(navDirections)
        }

        getAvailableBooks()
    }

//    TODO: Pending Refactor with LiveData
//    TODO: Pending Refactor with Coroutines
//    TODO: Pending Refactor with ServiceLocator
//    TODO: Pending Refactor into a ViewModel
    private fun getAvailableBooks() {

        RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
            .create<CurrenciesService>().listAvailableBooks().enqueue(
                object : Callback<BitsoWrapper<List<Book>>> {
                    override fun onResponse(
                        call: Call<BitsoWrapper<List<Book>>>,
                        response: Response<BitsoWrapper<List<Book>>>
                    ) {
                        val message = "Available books count: ${response.body()?.payload?.size}"
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                        Log.e("ASCT", message)
                    }

                    override fun onFailure(call: Call<BitsoWrapper<List<Book>>>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }
}
