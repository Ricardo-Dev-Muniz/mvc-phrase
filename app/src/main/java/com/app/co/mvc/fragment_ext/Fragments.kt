package com.app.co.mvc.fragment_ext

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun Fragment.destroy(
    _binding: ViewDataBinding? = null
) = run {
    onDestroyView()
    onDestroy()
    _binding
}

fun Fragment.share(
    context: Context,
    description: String,
    bitmap: Bitmap? = null,
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
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    } catch (e: Exception) {
        System.err.println(e.toString())
    }

    share.putExtra(Intent.EXTRA_TEXT, description)
    share.putExtra(Intent.EXTRA_STREAM, uri)
    context.startActivity(Intent.createChooser(share, "Compartilhar evento"))
}