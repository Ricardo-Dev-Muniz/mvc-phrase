package com.app.co.core.interfaces

import com.app.co.core.data.Page

interface SafeClick {
    fun item(page: Page?): Page
}