package com.github.alvarosct02.criptocurrency

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.math.BigDecimal

object BindingUtils {

    @JvmStatic
    fun colorFromNumber(number: BigDecimal?): Int {
        return if (number?.signum() == -1) Color.RED else Color.GREEN
    }
}

@BindingAdapter("isVisible")
fun View.isViewVisible(value: Boolean?) {
    this.isVisible = value ?: false
}

@BindingAdapter("items")
fun <T> RecyclerView.setItems(items: List<T>?) {
    items?.let {
        (this.adapter as? ListAdapter<T, *>)?.submitList(items)
    }
}

@BindingAdapter("chartData", "chartColor")
fun LineChart.setTickerData(uiState: UIState<List<TickerHistory>>?, @ColorInt chartColor: Int) {
    val tickerHistory = uiState?.data ?: listOf()
    val entries: List<Entry> = tickerHistory.map {
        Entry(it.bucketStartTime.toFloat(), it.lastRate.toFloatOrNull() ?: 0f)
    }
    val dataSet = LineDataSet(entries, "Label").apply {
        setDrawValues(false)
        setDrawCircles(false)
        color = chartColor
    }
    val lineData = LineData(dataSet)
    this.apply {
        data = lineData
        axisLeft.setDrawGridLines(false)
        axisLeft.isEnabled = false
        axisRight.isEnabled = false
        axisRight.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        xAxis.isEnabled = false
        legend.isEnabled = false
        description.isEnabled = false
        setViewPortOffsets(0f, 0f, 0f, 0f)
        invalidate() // refresh
    }
}

@BindingAdapter("cryptoIcon")
fun ImageView.setIcon(book: String?) {
    book?.split("_")?.firstOrNull()?.let {
        val drawableId = resources.getIdentifier("ic_$it", "drawable", context.packageName)
        val drawable = ContextCompat.getDrawable(context, drawableId)
        this.setImageDrawable(drawable)
    }
}
