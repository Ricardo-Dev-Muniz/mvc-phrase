package com.app.co.mvc.controller

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.co.core.adapter.ViewPagerAdapter
import com.app.co.core.data.Page
import com.app.co.mvc.R
import com.app.co.mvc.databinding.FragmentHomeBinding
import com.app.co.mvc.support_ext.setColouredSpan
import com.google.android.material.tabs.TabLayoutMediator

class Controller {
    val setColor = fun(text: TextView, context: Context) {
        val color by lazy { ContextCompat.getColor(context, R.color.pink) }
        text.setColouredSpan(
            text.text.toString(), 0, 5, color
        )
    }

    fun adapter(view: FragmentHomeBinding, context: Context, page: Page?) {
        view.viewPager.adapter =
            ViewPagerAdapter(context, mutableListOf(page))
        TabLayoutMediator(view.dots, view.viewPager) { _, _ ->
        }.attach()

        view.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }
}