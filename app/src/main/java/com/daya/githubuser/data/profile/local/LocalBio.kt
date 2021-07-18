package com.daya.githubuser.data.profile.local

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocalBio(
    var avatar: String,
    val company: String,
    val follower: Int,
    val following: Int,
    var location: String,
    val name: String,
    val repository: Int,
    val username: String
)