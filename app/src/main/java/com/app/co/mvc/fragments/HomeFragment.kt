package com.app.co.mvc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.co.core.adapter.ViewPagerAdapter
import com.app.co.core.data.Page
import com.app.co.core.module.App
import com.app.co.core.module.Utils
import com.app.co.core.support_ext.share
import com.app.co.core.viewmodel.HomeViewModel
import com.app.co.mvc.databinding.FragmentHomeBinding
import com.app.co.mvc.fragment_ext.destroy
import com.app.co.mvc.interfaces.AdapterCallbacks
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), AdapterCallbacks {

    private val modules by lazy { Utils.loadModules(App.modules()) }
    private fun insertModules() = modules
    private val viewModel: HomeViewModel by sharedViewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        insertModules()
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
            updatePage(it)
        }
    }

    private fun click() {
        binding.btnShare.setOnClickListener {
            val page = viewModel.getPageItem(viewModel.position.value!!)
            share(requireContext(), page.citation!!)
        }

        binding.btnNext.setOnClickListener {
            binding.viewPager.setCurrentItem(2, true)
        }
    }

    override fun updatePage(mutableList: List<Page?>) {
        adapter = ViewPagerAdapter(requireContext(), mutableList.toMutableList())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.dots, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.setPosition(position)
                super.onPageSelected(position)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        destroy()
    }
}