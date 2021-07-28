package com.incrediblehohol.data.remote

import com.incrediblehohol.core.extensions.orIfNull
import com.incrediblehohol.data.remote.utils.ReqResApi
import com.incrediblehohol.data.remote.utils.UserDTO
import java.io.InvalidObjectException
import javax.inject.Inject

internal class RemoteDataSource @Inject constructor(private val reqApi: ReqResApi) :
    IRemoteDataSource {

    override suspend fun getUsersByPage(page: Int): List<UserDTO> {
        return reqApi.getUsers(page).data.orIfNull { throw InvalidObjectException("data is null") }
    }
}

internal interface IRemoteDataSource {
    suspend fun getUsersByPage(page: Int): List<UserDTO>
}