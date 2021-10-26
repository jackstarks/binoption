package com.newoption.binatraderapps.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newoption.binatraderapps.R
import com.newoption.binatraderapps.databinding.ItemPostBinding
import com.newoption.binatraderapps.model.User
import com.newoption.binatraderapps.ui.fragments.MainViewModel


class PostAdapter(
    private val postArrayList: MutableList<User>,
    private val viewModel: MainViewModel
) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val binding: ItemPostBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_post, parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        val post = postArrayList[position]
        viewHolder.binding.viewModel = viewModel
        viewHolder.binding.post = post
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return postArrayList.size
    }

    inner class ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)
}