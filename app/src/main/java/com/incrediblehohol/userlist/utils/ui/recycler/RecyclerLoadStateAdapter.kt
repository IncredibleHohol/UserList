package com.incrediblehohol.userlist.utils.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.incrediblehohol.userlist.databinding.ItemUserLoaderBinding


class RecyclerLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RecyclerLoadStateAdapter.LoaderViewHolder>() {


    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.onBind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserLoaderBinding.inflate(layoutInflater, parent, false)
        return LoaderViewHolder(binding)
    }


    class LoaderViewHolder(private val binding: ItemUserLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(loadState: LoadState, retry: () -> Unit) {
            binding.btnRetry.setOnClickListener { retry.invoke() }
            if (loadState is LoadState.Loading) {
                binding.btnRetry.visibility = View.INVISIBLE
                binding.tvError.visibility = View.GONE
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.btnRetry.visibility = View.VISIBLE
                binding.tvError.visibility = View.VISIBLE
                binding.pbLoading.visibility = View.GONE
            }
        }
    }
}
