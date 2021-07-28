package com.incrediblehohol.data.remote.utils


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("total")
    val total: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("data")
    val data: List<UserDTO>? = null,
    @SerializedName("support")
    val support: Support? = null
)

@Entity(tableName = "user")
data class UserDTO(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null
)