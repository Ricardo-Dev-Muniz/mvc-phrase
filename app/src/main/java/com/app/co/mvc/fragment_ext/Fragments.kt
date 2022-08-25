package com.app.co.mvc.fragment_ext

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun Fragment.destroy(
    _binding: ViewDataBinding? = null
) = run {
    onDestroyView()
    onDestroy()
    _binding
}