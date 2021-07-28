package com.incrediblehohol.userlist.di

import android.content.Context
import com.incrediblehohol.data.di.DataModule
import com.incrediblehohol.domain.di.DomainModule
import com.incrediblehohol.userlist.App
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class, DomainModule::class])
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext
}