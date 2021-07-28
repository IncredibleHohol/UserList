package com.incrediblehohol.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUI(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String? = null
) : Parcelable