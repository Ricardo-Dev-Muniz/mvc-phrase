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

    private val _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> = _position

    init {
        viewModelScope.launch {
            getPage()
        }
    }

    private fun getPage() = viewModelScope.launch {
       withContext(Dispatchers.IO) {
            repository.getPage()
        }.read({
           _page.value = it
       }, {
            Log.e("home_view_model", "Error viewModel - ${it.message}")
        })
    }

    fun setPosition(position: Int) {
        _position.value = position
    }
    fun getPageItem(position: Int): Page {
        return _page.value!![position]!!
    }

    override fun onReceiveResponse(code: Int, data: Any?) {
        Log.d("home_view_model",
            "OnReceiveResponse viewModel - code: $code ==> data: $data")
    }
}