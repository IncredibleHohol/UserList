package com.incrediblehohol.domain.di

import com.incrediblehohol.data.remote.utils.UserDTO
import com.incrediblehohol.domain.IInteractor
import com.incrediblehohol.domain.Interactor
import com.incrediblehohol.domain.contracts.IMapper
import com.incrediblehohol.domain.mappers.UserUiMapper
import com.incrediblehohol.domain.models.UserUI
import dagger.Binds
import dagger.Module

@Module(includes = [DomainBinds::class])
class DomainModule

@Module
internal interface DomainBinds {
    @Binds
    fun bindsInteractor(interactor: Interactor): IInteractor

    @Binds
    fun bindsMapper(userUiMapper: UserUiMapper): IMapper<UserDTO, UserUI>
}