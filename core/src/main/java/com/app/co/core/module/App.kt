package com.app.co.core.module

import com.app.co.core.repository.HomeRepository
import com.app.co.core.repository.HomeRepositoryImpl
import com.app.co.core.repository.Service
import com.app.co.core.service.ServiceGen
import com.app.co.core.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object App {

    fun modules() = arrayListOf(
        module,
        repository,
        service
    )

    private val module = module {
        viewModel { HomeViewModel(androidApplication(), repository = get()) }
    }

    private val repository = module {
        single<HomeRepository> { HomeRepositoryImpl(get()) }
    }

    private val service = module {
        single {
            ServiceGen(
                url = "https://us-central1-crawler-apiv1.cloudfunctions.net/app/",
                headers = listOf(
                    Pair("Content-Type", "application/json"),
                    Pair("Accept", "application/json"),
                    Pair("Connection", "close")
                )
            ).generate() as Service
        }
    }
}