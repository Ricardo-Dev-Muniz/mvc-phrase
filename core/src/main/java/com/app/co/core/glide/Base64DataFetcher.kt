package com.app.co.core.glide

import android.annotation.SuppressLint
import android.util.Base64
import androidx.annotation.NonNull
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.nio.ByteBuffer

class Base64DataFetcher : DataFetcher<ByteBuffer> {
    private var model: String? = null

    @SuppressLint("NotConstructor")
    fun base64DataFetcher(model: String?) {
        this.model = model
    }

    private fun getBase64SectionOfModel(): String {
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs.
        val startOfBase64Section = model!!.indexOf(',')
        return model!!.substring(startOfBase64Section + 1)
    }

    override fun cleanup() {
        // Intentionally empty only because we're not opening an InputStream or another I/O resource!
    }

    override fun cancel() {
        // Intentionally empty.
    }

    @NonNull
    override fun getDataClass(): Class<ByteBuffer> {
        return ByteBuffer::class.java
    }

    @NonNull
    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in ByteBuffer>) {
        val base64Section = getBase64SectionOfModel()
        val data: ByteArray = Base64.decode(base64Section, Base64.DEFAULT)
        val byteBuffer = ByteBuffer.wrap(data)
        callback.onDataReady(byteBuffer)
    }
}