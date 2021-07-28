package com.incrediblehohol.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.incrediblehohol.data.local.UserDao
import com.incrediblehohol.data.remote.utils.UserDTO
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
internal class Repository
@Inject constructor(
    private val userDao: UserDao,
    private val usersMediator: UsersMediator
) : IRepository {


    override fun getUsersObservable(pagingConfig: PagingConfig?): Observable<PagingData<UserDTO>> {
        return Pager(
            config = pagingConfig ?: getDefaultPageConfig(),
            pagingSourceFactory = { userDao.getAllUsers() },
            remoteMediator = usersMediator
        ).observable
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 6, enablePlaceholders = false)
    }
}

interface IRepository {
    fun getUsersObservable(pagingConfig: PagingConfig?): Observable<PagingData<UserDTO>>
}