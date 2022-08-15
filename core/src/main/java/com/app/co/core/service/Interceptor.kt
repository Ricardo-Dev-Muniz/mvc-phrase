package com.app.co.core.service

import okhttp3.Interceptor
import okhttp3.Response

object Interceptor : Interceptor {
    private var onResponseListener: OnResponseListener? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        onResponseListener?.onReceiveResponse(response.code, response.body)
        return response
    }

    interface OnResponseListener {
        fun onReceiveResponse(code: Int, data: Any?)
    }
}