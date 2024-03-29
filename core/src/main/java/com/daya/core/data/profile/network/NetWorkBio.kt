package com.daya.core.data.profile.network

import com.daya.core.utils.NullableStringField
import com.squareup.moshi.JsonClass
//this class used to carry response data from retrofit
@JsonClass(generateAdapter = true)
data class NetWorkBio(
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,

    @NullableStringField
    var name: String = "",
    @NullableStringField
    var company: String = "",
    @NullableStringField
    var location: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val public_repos: Int = 0
)