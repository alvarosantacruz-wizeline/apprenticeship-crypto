package com.github.alvarosct02.criptocurrency

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal

object BindingUtils {

    @JvmStatic
    fun colorFromNumber(number: BigDecimal): Int {
        return if (number.signum() == -1) Color.RED else Color.GREEN
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

@BindingAdapter("cryptoIcon")
fun ImageView.setIcon(book: String?) {
    book?.split("_")?.firstOrNull()?.let {
        val drawableId = resources.getIdentifier("ic_$it", "drawable", context.packageName)
        val drawable = ContextCompat.getDrawable(context, drawableId)
        this.setImageDrawable(drawable)
    }
}
