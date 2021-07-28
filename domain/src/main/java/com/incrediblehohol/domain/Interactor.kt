package com.incrediblehohol.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.incrediblehohol.data.IRepository
import com.incrediblehohol.data.remote.utils.UserDTO
import com.incrediblehohol.domain.contracts.IMapper
import com.incrediblehohol.domain.models.UserUI
import io.reactivex.Observable
import javax.inject.Inject

internal class Interactor @Inject constructor(
    private val repository: IRepository,
    private val uiMapper: IMapper<UserDTO, UserUI>
) : IInteractor {

    override fun getUsers(): Observable<PagingData<UserUI>> {
        return repository.getUsersObservable(
            PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                initialLoadSize = 6,
                maxSize = 18,
                prefetchDistance = 6,
                jumpThreshold = 6
            )
        )
            .map { pagingData ->
                pagingData.map {
                    uiMapper.map(it)
                }
            }
    }
}

interface IInteractor {
    fun getUsers(): Observable<PagingData<UserUI>>
}

