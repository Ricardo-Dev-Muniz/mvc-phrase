package com.app.co.mvc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.co.mvc.activity_ext.finalize
import com.app.co.mvc.activity_ext.screenFit
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenFit()
        setContentView(R.layout.activity_main)
        view()
    }

    private fun view() {
        MobileAds.initialize(this){}
        adView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finalize()
    }
}