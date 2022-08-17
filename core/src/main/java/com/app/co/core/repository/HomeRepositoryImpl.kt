package com.app.co.core.repository

import com.app.co.core.call.Service
import com.app.co.core.data.Page
import com.app.co.core.response_ext.ResponseAny
import com.app.co.core.service.safeApiCall

class HomeRepositoryImpl(var api: Service): HomeRepository {
    override suspend fun getPage(): ResponseAny<Page?> = safeApiCall { api.getPage() }
    override suspend fun log(): ResponseAny<Any> = safeApiCall { api.log() }
}