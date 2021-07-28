package com.incrediblehohol.userlist.utils.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.incrediblehohol.domain.models.UserUI
import com.incrediblehohol.userlist.BR
import com.incrediblehohol.userlist.R
import com.incrediblehohol.userlist.databinding.ItemUserBinding

class UsersRecyclerAdapter(
    private val onItemClick: ((UserUI) -> Unit)? = null,
    private val lifecycleOwner: LifecycleOwner? = null
) : PagingDataAdapter<UserUI, UsersRecyclerAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<UserUI>() {
            override fun areItemsTheSame(oldItem: UserUI, newItem: UserUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserUI, newItem: UserUI): Boolean =
                oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(
                item = it,
                onItemClick = onItemClick,
                lifecycleOwner = lifecycleOwner
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemUserBinding>(
            layoutInflater,
            R.layout.item_user,
            parent,
            false
        )

        return ViewHolder(binding)
    }



    class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            item: UserUI,
            onItemClick: ((UserUI) -> Unit)?,
            lifecycleOwner: LifecycleOwner?
        ) {
            onItemClick?.let { onClick ->
                binding.root.setOnClickListener {
                    onClick.invoke(item)
                }
            }
            binding.setVariable(BR.item, item)
            binding.lifecycleOwner = lifecycleOwner
        }
    }
}