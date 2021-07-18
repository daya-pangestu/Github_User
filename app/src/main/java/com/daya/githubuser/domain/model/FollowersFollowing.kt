package com.daya.githubuser.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersFollowing(
        var followUsername : String,
        val avatarUrl : String
) : Parcelable