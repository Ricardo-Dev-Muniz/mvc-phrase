package com.app.co.mvc.support_ext

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun Any?.launchImageGlide(
    url: String?, size: Int,
    context: Context,
    callback: (bitmap: Bitmap) -> Unit,
) = GlobalScope.launch {
    withContext(Dispatchers.Main) {
        (context as Activity).runOnUiThread {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(
                    RequestOptions().format(
                        DecodeFormat.PREFER_ARGB_8888
                    )
                ).skipMemoryCache(true)
                .into(object : CustomTarget<Bitmap?>(size, size) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        callback(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {}

                    override fun onLoadCleared(placeholder: Drawable?) {}

                })
        }
    }
}

fun TextView.setColouredSpan(
    word: String,
    start: Int, end: Int,
    color: Int,
) {
    this.text = word
    val spannableString = SpannableString(text)
    try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        println("$word was not not found in TextView text")
    }
}