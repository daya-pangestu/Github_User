package com.daya.githubuser.data.profile.local

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocalUsers(
    val users: List<LocalBio>
)