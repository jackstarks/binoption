package com.newoption.binatraderapps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.newoption.binatraderapps.R
import com.newoption.binatraderapps.databinding.SlideFragmentBinding


class SlideFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: SlideFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.slide_fragment, container, false
        )
        binding.fragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(
            SlideModel(
                R.drawable.ic1,
                ""
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.ic2,
                ""
            )
        )

        imageList.add(SlideModel(R.drawable.ic3, ""))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }


    override fun onClick(p0: View?) {
        findNavController().popBackStack()
    }

}


