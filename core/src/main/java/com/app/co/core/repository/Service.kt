package com.app.co.core.repository

import com.app.co.core.data.Page
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("phrases")
    suspend fun getPage(): Response<List<Page?>>
}