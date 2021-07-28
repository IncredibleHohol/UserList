package com.incrediblehohol.data.utils

import com.incrediblehohol.data.BuildConfig
import com.incrediblehohol.data.utils.contracts.INetworkService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val NETWORK_CALL_TIMEOUT: Long = 20 * 1000
const val NETWORK_CONNECT_TIMEOUT: Long = 20 * 1000
const val NETWORK_READ_TIMEOUT: Long = 20 * 1000
const val NETWORK_WRITE_TIMEOUT: Long = 20 * 1000

class NetworkService @Inject constructor() : INetworkService {

    private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(NETWORK_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(NETWORK_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })


    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BuildConfig.REQRES_URL)
        .client(clientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    override fun <T> createService(apiClass: Class<T>, baseUrl: String): T =
        retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(apiClass)
}