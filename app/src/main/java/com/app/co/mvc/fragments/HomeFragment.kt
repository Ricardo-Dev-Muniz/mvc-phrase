package com.app.co.mvc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.co.core.module.App
import com.app.co.core.module.Utilities
import com.app.co.core.viewmodel.HomeViewModel
import com.app.co.mvc.controller.Controller
import com.app.co.mvc.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val injectModules by lazy { Utilities.loadModules(App.modules()) }
    private fun injectModules() = injectModules
    private val viewModel: HomeViewModel by sharedViewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var controller: Controller = Controller()

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
        viewModel.getPage()
        viewModel.page.observe(viewLifecycleOwner) {
            controller.adapter(binding, requireContext(), it)
        }
    }

    private fun click() {
        binding.cardView.setOnClickListener {

        }
    }
}