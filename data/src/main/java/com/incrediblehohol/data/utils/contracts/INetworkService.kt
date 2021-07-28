package com.incrediblehohol.data.utils.contracts

interface INetworkService {
    fun <T> createService(apiClass: Class<T>, baseUrl: String): T
}