package com.daya.core.data.profile.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
//used only to only get outer response from retrofit, meaning its useless
@JsonClass(generateAdapter = true)
data class NetWorkUsers(
    val incomplete_results: Boolean,
    @Json(name = "items")
    val listNetWorkBio: List<NetWorkBio>,
    val total_count: Int
)