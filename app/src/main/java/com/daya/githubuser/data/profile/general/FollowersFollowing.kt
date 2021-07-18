package com.daya.githubuser.data.profile.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersFollowing(
        var followUsername : String,
        val avatarUrl : String
) : Parcelable