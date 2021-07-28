package com.incrediblehohol.userlist.utils.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

open class BaseViewModel : ViewModel() {
    private val _action = MutableLiveData<Event<NavDirections>>()
    val action: LiveData<Event<NavDirections>> = _action


    internal fun navigate(action: NavDirections) {
        _action.value = Event(action)
    }
}