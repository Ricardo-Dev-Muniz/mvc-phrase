package com.app.co.core.call

import com.app.co.core.data.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @POST(value = "citation")
    suspend fun getPage(): Response<Page?>

    @GET("log")
    suspend fun log(): Response<Any>
}