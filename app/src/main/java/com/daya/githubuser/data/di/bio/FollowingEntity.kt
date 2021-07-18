package com.daya.githubuser.data.di.bio

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FollowingEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "following_user_name")
    val followingUserName: String,

    val userName :String,

    @ColumnInfo(name = "follower_avatar_url")
    val avatarUrl: String
)