package com.incrediblehohol.userlist.ui.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.incrediblehohol.userlist.databinding.FragmentUsersBinding
import com.incrediblehohol.userlist.di.base.BaseViewModelFactory
import com.incrediblehohol.userlist.utils.ui.base.BaseFragment
import com.incrediblehohol.userlist.utils.ui.recycler.RecyclerLoadStateAdapter
import com.incrediblehohol.userlist.utils.ui.recycler.SimpleSpacesItemDecoration
import com.incrediblehohol.userlist.utils.ui.recycler.UsersRecyclerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UsersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UsersViewModelFactory

    @Inject
    lateinit var simpleSpacesItemDecoration: SimpleSpacesItemDecoration

    override val vm by viewModels<UsersViewModel> {
        BaseViewModelFactory(viewModelFactory, this)
    }

    private val adapter: UsersRecyclerAdapter by lazy {
        UsersRecyclerAdapter(
            onItemClick = { vm.onUser(it) },
            lifecycleOwner = viewLifecycleOwner
        )
    }

    private val loadStateAdapter: RecyclerLoadStateAdapter by lazy {
        RecyclerLoadStateAdapter { adapter.retry() }
    }

    private var _binding: FragmentUsersBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        fetchData()
    }

    private fun initView() {
        binding.rvUsers.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        binding.rvUsers.addItemDecoration(simpleSpacesItemDecoration)
        adapter.addLoadStateListener(vm::onLoadState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.btnRetry.setOnClickListener { adapter.refresh() }
    }

    @SuppressLint("CheckResult")
    private fun fetchData() {
        vm.getUsers().subscribe {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}