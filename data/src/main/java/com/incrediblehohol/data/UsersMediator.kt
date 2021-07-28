package com.incrediblehohol.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.incrediblehohol.data.local.*
import com.incrediblehohol.data.remote.IRemoteDataSource
import com.incrediblehohol.data.remote.utils.UserDTO
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
internal class UsersMediator @Inject constructor(
    private val db: UsersDatabase,
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource,
    private val keysDao: RemoteKeysDao,
    private val userDao: UserDao
) : RemoteMediator<Int, UserDTO>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDTO>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val users = remoteDataSource.getUsersByPage(page)
            val isEndOfList = users.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    keysDao.clearRemoteKeys()
                    userDao.clearDb()
                }
                val prevKey = if (page == DEFAULT_PAGE) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = users.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                keysDao.insertAll(keys)
                userDao.insertUsers(users)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, UserDTO>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                val key = remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE
                key
            }
            LoadType.APPEND -> {
                val key = keysDao.getAllKeys().lastOrNull()
                key?.nextKey ?: return MediatorResult.Success(true)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKeys(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private suspend fun getFirstRemoteKeys(state: PagingState<Int, UserDTO>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user ->
                keysDao.remoteKeysUserId(user.id)
            }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, UserDTO>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                keysDao.remoteKeysUserId(repoId)
            }
        }
    }

    companion object {
        internal const val DEFAULT_PAGE = 1
    }
}