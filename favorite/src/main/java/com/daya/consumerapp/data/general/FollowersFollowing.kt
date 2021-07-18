package com.daya.consumerapp.data.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersFollowing(
        var followUsername : String,
        val avatarUrl : String
) : Parcelable