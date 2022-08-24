package com.app.co.mvc.interfaces

import com.app.co.core.data.Page

interface AdapterCallbacks {
    fun updatePage(mutableList: List<Page?>)
}