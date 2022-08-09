package com.app.co.core.call

import com.app.co.core.data.Page
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("citation")
    suspend fun getPage(): Response<Page?>
}