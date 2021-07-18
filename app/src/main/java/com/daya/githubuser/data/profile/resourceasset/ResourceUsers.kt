package com.daya.githubuser.data.profile.resourceasset

import com.squareup.moshi.JsonClass
//used only to only get outer response json in raw folder, meaning its useless
@JsonClass(generateAdapter = true)
data class ResourceUsers(
    val users: List<ResourceBio>
)