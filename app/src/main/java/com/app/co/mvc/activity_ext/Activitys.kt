package com.app.co.mvc.activity_ext

import android.app.Activity
import android.content.Intent

fun Activity.finalize() = finish()
fun Activity.exit(act: Activity) =
    act.startActivity(Intent(this, act::class.java))
