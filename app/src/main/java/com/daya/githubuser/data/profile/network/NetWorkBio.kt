package com.daya.githubuser.data.profile.network

import com.daya.githubuser.xtension.NullableStringField
import com.squareup.moshi.JsonClass

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