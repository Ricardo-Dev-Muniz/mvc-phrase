package com.app.co.core.response_ext

fun <T> ResponseAny<T>.read(
    success: (T) -> Unit,
    error: ((Exception) -> Unit)? = null,
    empty: ((Int) -> Unit)? = null
) {
    when (this) {
        is ResponseSuccess -> success(this.body)
        is ResponseError -> error?.invoke(this.exception)
        is ResponseEmpty -> empty?.invoke(this.code)
    }
}