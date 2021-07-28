package com.incrediblehohol.userlist.ui.users

import com.incrediblehohol.domain.IInteractor
import com.incrediblehohol.userlist.di.base.ViewModelAssistedFactory
import javax.inject.Inject

class UsersViewModelFactory @Inject constructor(private val interactor: IInteractor) :
    ViewModelAssistedFactory<UsersViewModel> {

    override fun create(): UsersViewModel = UsersViewModel(interactor)
}