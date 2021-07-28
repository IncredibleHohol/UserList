package com.incrediblehohol.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.incrediblehohol.data.BuildConfig
import com.incrediblehohol.data.remote.utils.UserDTO

@Database(
    entities = [UserDTO::class, RemoteKeys::class],
    version = BuildConfig.ROOM_VERSION,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getKeysDao(): RemoteKeysDao
}

@Entity
data class RemoteKeys(
    @PrimaryKey val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remotekeys WHERE repoId = :id")
    suspend fun remoteKeysUserId(id: Int): RemoteKeys?

    @Query("DELETE FROM remotekeys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM remotekeys")
    suspend fun getAllKeys(): List<RemoteKeys?>
}

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserDTO>)

    @Query("SELECT * FROM user")
    fun getAllUsers(): PagingSource<Int, UserDTO>

    @Query("DELETE FROM user")
    suspend fun clearDb()
}