package com.incrediblehohol.userlist.ui.users.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.incrediblehohol.userlist.databinding.FragmentUserDetailBinding
import com.incrediblehohol.userlist.utils.ui.base.BaseFragment

class UserDetailFragment : BaseFragment() {

    private val args by navArgs<UserDetailFragmentArgs>()
    private var _binding: FragmentUserDetailBinding? = null

    private val binding: FragmentUserDetailBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = args.userUI
    }
}