package com.incrediblehohol.data.local

import com.incrediblehohol.data.remote.utils.UserDTO
import javax.inject.Inject

internal class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val keysDao: RemoteKeysDao
) : ILocalDataSource {

    override suspend fun clearData() {
        keysDao.clearRemoteKeys()
        userDao.clearDb()
    }

    override suspend fun saveData(remoteKeys: List<RemoteKeys>, users: List<UserDTO>) {
        keysDao.insertAll(remoteKeys)
        userDao.insertUsers(users)
    }

    override suspend fun getAllKeys(): List<RemoteKeys?> {
        return keysDao.getAllKeys()
    }

    override suspend fun getKeyById(id: Int): RemoteKeys? {
        return keysDao.remoteKeysUserId(id)
    }
}


internal interface ILocalDataSource {
    suspend fun clearData()
    suspend fun saveData(remoteKeys: List<RemoteKeys>, users: List<UserDTO>)
    suspend fun getAllKeys(): List<RemoteKeys?>
    suspend fun getKeyById(id: Int): RemoteKeys?
}