package com.app.co.core.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.co.core.data.Page
import com.app.co.core.repository.HomeRepository
import com.app.co.core.response_ext.read
import com.app.co.core.service.Interceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    application: Application,
    private val repository: HomeRepository,
) : AndroidViewModel(application),
    Interceptor.OnResponseListener {

    private val _page: MutableLiveData<List<Page?>> = MutableLiveData()
    val page: LiveData<List<Page?>> = _page

    init {
        viewModelScope.launch {
            getPage()
        }
    }

    private fun getPage() = viewModelScope.launch {
       withContext(Dispatchers.IO) {
            repository.getPage()
        }.read({
           Log.e("result_vm", "viewModel - ${it.toList()}")
           _page.value = it
            Log.e("result_vm", "viewModel - $it")
       }, {
            Log.e("", "Error viewModel - ${it.message}")
        })
    }

    /** future random list type phrases */
    private fun <E> List<E>.doRandom(random: java.util.Random): E? =
        if (size > 0) get(random.nextInt(size)) else null

    override fun onReceiveResponse(code: Int, data: Any?) {
        Log.d("", "OnReceiveResponse viewModel - code: $code ==> data: $data")
    }
}