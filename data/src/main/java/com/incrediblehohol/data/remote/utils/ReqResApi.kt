package com.incrediblehohol.data.remote.utils

import retrofit2.http.GET
import retrofit2.http.Query

interface ReqResApi {

//    @GET("/api/users")
//    fun getUsersByPage(@Query("page") page: Int): Single<UsersResponse>

    @GET("/api/users")
    suspend fun getUsers(@Query("page") page: Int): UsersResponse
}