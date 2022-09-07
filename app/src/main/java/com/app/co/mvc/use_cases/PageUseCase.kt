package com.app.co.mvc.use_cases

import com.app.co.core.repository.HomeRepository

class PageUseCase(private val repository: HomeRepository) {
    suspend operator  fun invoke() = repository.getPage()
}