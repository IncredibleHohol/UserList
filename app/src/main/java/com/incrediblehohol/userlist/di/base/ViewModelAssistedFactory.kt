package com.incrediblehohol.userlist.di.base

import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(): T
}