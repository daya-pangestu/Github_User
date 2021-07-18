package com.daya.consumerapp.data.bio


data class BioEntity(
    val username: String, // or login
    var avatar: String,
    val company: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    var location: String = "",
    val name: String = "",
    val repoCount: Int = 0,
    val followersUrl: String = "",
    val followingUrl: String = "",
)