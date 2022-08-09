package com.app.co.mvc.fragment_ext

import androidx.fragment.app.Fragment

fun Fragment.destroy() = run {
    onDestroyView()
    onDestroy()
}
