package com.daya.core.data.profile.resourceasset

import com.squareup.moshi.JsonClass
//this class used to carry data from githubuser.json
@JsonClass(generateAdapter = true)
data class ResourceBio(
    var avatar: String,
    val company: String,
    val follower: Int,
    val following: Int,
    var location: String,
    val name: String,
    val repository: Int,
    val username: String
)