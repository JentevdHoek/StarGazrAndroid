package com.example.stargazrandroid.model

import com.google.gson.annotations.SerializedName

data class APODModel(
    @SerializedName("date") val date: String,
    @SerializedName("explanation") var explanation: String,
    @SerializedName("hdurl") val hdurl: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("title") var title: String,
    @SerializedName("url") val url: String,
    @SerializedName("copyright") val copyright: String?
)