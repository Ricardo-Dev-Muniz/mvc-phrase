package com.app.co.mvc.activity_ext

import android.app.Activity
import android.view.WindowManager

fun Activity.finalize() = finish()

fun Activity.screenFit() {
    window.clearFlags(0)
    window.clearFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                and WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}