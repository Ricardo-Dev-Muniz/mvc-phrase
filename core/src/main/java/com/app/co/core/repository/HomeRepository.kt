package com.app.co.core.repository

import com.app.co.core.data.Page
import com.app.co.core.response_ext.ResponseAny

interface HomeRepository {
    suspend fun getPage(): ResponseAny<List<Page?>>
}