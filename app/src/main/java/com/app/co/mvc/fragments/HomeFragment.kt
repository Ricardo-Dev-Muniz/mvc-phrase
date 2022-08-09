package com.app.co.mvc.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.co.core.adapter.ViewPagerAdapter
import com.app.co.core.data.Page
import com.app.co.core.module.App
import com.app.co.core.module.Utilities
import com.app.co.core.viewmodel.HomeViewModel
import com.app.co.mvc.databinding.FragmentHomeBinding
import com.app.co.mvc.fragment_ext.destroy
import com.app.co.mvc.fragment_ext.share
import com.app.co.mvc.interfaces.AdapterCallbacks
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), AdapterCallbacks {

    private val injectModules by lazy { Utilities.loadModules(App.modules()) }
    private fun injectModules() = injectModules
    private val viewModel: HomeViewModel by sharedViewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
            .apply { lifecycleOwner = viewLifecycleOwner }
        observer()
        click()
        return binding.root
    }

    private fun observer() {
        viewModel.page.observe(viewLifecycleOwner) {
           updatePage(mutableListOf(it))
        }
    }

    private fun click() {
        binding.btnShare.setOnClickListener {
            share(requireContext(), "")
        }
    }

    override fun updatePage(mutableList: MutableList<Page?>) {
        binding.viewPager.adapter =
            ViewPagerAdapter(requireContext(), mutableList)
        TabLayoutMediator(binding.dots, binding.viewPager) { _, _ ->
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("home_fragment", "Adapter position $position")
                super.onPageSelected(position)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        destroy(_binding)
    }
}