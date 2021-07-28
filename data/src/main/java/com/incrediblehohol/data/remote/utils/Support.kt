package com.incrediblehohol.data.remote.utils


import com.google.gson.annotations.SerializedName

data class Support(
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("text")
    val text: String? = null
)