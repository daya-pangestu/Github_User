package com.daya.githubuser.data.di.bio

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class FollowersEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "follower_user_name")
    val followersUserName: String,

    val userName :String,

    @ColumnInfo(name = "follower_avatar_url")
    val avatarUrl: String
)