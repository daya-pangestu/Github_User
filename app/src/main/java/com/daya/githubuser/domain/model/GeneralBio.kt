package com.daya.githubuser.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralBio(
    var avatar: String,
    val company: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    var location: String = "",
    val name: String = "",
    val repoCount: Int = 0,
    val username: String, // or login
    val followersUrl : String = "",
    val followingUrl : String = "",
    var followers : List<FollowersFollowing> = emptyList(),
    var followings : List<FollowersFollowing> = emptyList()

) : Parcelable