package com.incrediblehohol.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.incrediblehohol.data.BuildConfig
import com.incrediblehohol.data.IRepository
import com.incrediblehohol.data.Repository
import com.incrediblehohol.data.local.*
import com.incrediblehohol.data.remote.IRemoteDataSource
import com.incrediblehohol.data.remote.RemoteDataSource
import com.incrediblehohol.data.remote.utils.ReqResApi
import com.incrediblehohol.data.utils.NetworkService
import com.incrediblehohol.data.utils.contracts.INetworkService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [DataBinds::class])
class DataModule {


    @Provides
    internal fun provideReqResApi(networkService: INetworkService): ReqResApi {
        return networkService.createService(ReqResApi::class.java, BuildConfig.REQRES_URL)
    }

    @Singleton
    @Provides
    internal fun provideRoomDataBase(appContext: Context): UsersDatabase {
        return Room.databaseBuilder(appContext, UsersDatabase::class.java, "users.db").build()
    }

    @Provides
    internal fun provideUserDao(db: UsersDatabase): UserDao {
        return db.getUserDao()
    }

    @Provides
    internal fun provideKeyDao(db: UsersDatabase): RemoteKeysDao {
        return db.getKeysDao()
    }
}

@Module
internal interface DataBinds {

    @ExperimentalPagingApi
    @Binds
    fun bindRepository(repository: Repository): IRepository

    @Binds
    fun bindNetworkService(networkService: NetworkService): INetworkService

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    fun bindLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource

}