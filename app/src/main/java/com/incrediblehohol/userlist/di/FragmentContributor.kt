package com.incrediblehohol.userlist.di

import com.incrediblehohol.userlist.di.base.UsersScope
import com.incrediblehohol.userlist.ui.users.UsersFragment
import com.incrediblehohol.userlist.ui.users.detail.UserDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentContributor {

    @UsersScope
    @ContributesAndroidInjector
    abstract fun contributeUsersFragment(): UsersFragment

    @UsersScope
    @ContributesAndroidInjector
    abstract fun contributeUsersDetailFragment(): UserDetailFragment
}