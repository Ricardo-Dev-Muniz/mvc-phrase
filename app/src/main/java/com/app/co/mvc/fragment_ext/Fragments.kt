package com.app.co.mvc.fragment_ext

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.destroy() = run {
    requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleOnBackPressed()
            onDestroy()
        }
    })
}