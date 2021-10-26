package com.newoption.binatraderapps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.newoption.binatraderapps.R
import com.newoption.binatraderapps.databinding.MainFragmentBinding
import com.newoption.binatraderapps.model.User
import com.newoption.binatraderapps.ui.adapters.PostAdapter

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this.requireActivity()).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setRecyclerView()
    }


    private fun setRecyclerView() {
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewModel.users.observe(viewLifecycleOwner, object : Observer<MutableList<User>> {
            override fun onChanged(list: MutableList<User>) {
                adapter = PostAdapter(list, viewModel)
                binding.viewPager.adapter = adapter
                binding.springDotsIndicator.setViewPager2(binding.viewPager)
            }
        })
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == 1) {
                nextPage()
                viewModel.clearTouch()
            }
            if (hasFinished == -1) {
                previousPage()
                viewModel.clearTouch()
            }
            if (hasFinished == -2) {
                findNavController().popBackStack()
                viewModel.clearTouch()
            }
        })
    }

    private fun nextPage() {
        if (binding.viewPager.currentItem + 1 < binding.viewPager.adapter?.itemCount!!)
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
    }

    private fun previousPage() {
        if (binding.viewPager.adapter?.itemCount!! > 0)
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
    }


}



