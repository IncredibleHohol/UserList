package com.incrediblehohol.userlist.utils.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {
    protected open val vm by viewModels<BaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.action.observe(viewLifecycleOwner, this::onAction)
    }

    private fun onAction(event: Event<NavDirections>) {
        val action = event.contentIfNotHandled
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}