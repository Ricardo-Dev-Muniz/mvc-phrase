package com.app.co.mvc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.co.mvc.activity_ext.finalize

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finalize()
    }
}