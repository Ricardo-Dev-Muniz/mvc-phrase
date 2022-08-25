package com.app.co.core.support_ext

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

fun Any?.launchImage(
    uri: String?, size: Int,
    context: Context,
    callback: (bitmap: Bitmap) -> Unit,
) = GlobalScope.launch {
    withContext(Dispatchers.Main) {
        (context as Activity).runOnUiThread {
            Glide.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(null)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(
                    RequestOptions().format(
                    DecodeFormat.PREFER_ARGB_8888))
                .into(object : CustomTarget<Bitmap?>(size, size) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        callback(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}

                })

        }
    }
}

fun Any.share(
    context: Context,
    description: String,
    bitmap: Bitmap,
) {
    this.toString()
    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, "title")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    )!!

    try {
        val stream = context.contentResolver.openOutputStream(uri)!!
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    } catch (e: Exception) {
        System.err.println(e.toString())
    }

    share.putExtra(Intent.EXTRA_TEXT, description)
    share.putExtra(Intent.EXTRA_STREAM, uri)
    context.startActivity(Intent.createChooser(share, "Compartilhar evento"))
}
