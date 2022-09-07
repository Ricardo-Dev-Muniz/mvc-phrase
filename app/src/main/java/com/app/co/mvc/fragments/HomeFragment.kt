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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), AdapterCallbacks {

    private val modules by lazy { Utils.loadModules(App.modules()) }
    private fun insertModules() = modules
    private val viewModel: HomeViewModel by sharedViewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: ViewPagerAdapter? = null

    private var mInterstitialAd: InterstitialAd? = null

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
        inter()
        return binding.root
    }

    private fun observer() {
        viewModel.page.observe(viewLifecycleOwner) {
            adapter(it)
        }
    }

    private fun click() {
        binding.btnShare.setOnClickListener {
            binding.tvShare.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE

            binding.progress.animate().alpha(0f)
                .setDuration(3500).withEndAction {
                    binding.progress.visibility = View.GONE
                    binding.progress.alpha = 1F
                    binding.tvShare.visibility = View.VISIBLE
                    val page = viewModel.getPageItem(viewModel.position.value!!)
                    share(requireContext(), page.citation!!)
                    showInter()
                }
        }

        binding.btnNext.setOnClickListener {
            binding.viewPager.setCurrentItem(2, true)
        }
    }

    override fun adapter(mutableList: List<Page?>) {
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

    private fun inter() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),
            requireContext().getString(com.app.co.core.R.string.ads_inter),
            adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInter() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {}
            mInterstitialAd?.show(requireActivity())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        destroy()
    }
}