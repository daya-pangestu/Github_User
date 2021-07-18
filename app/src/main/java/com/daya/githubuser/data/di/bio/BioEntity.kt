package com.daya.githubuser.data.di.bio

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BioEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bio_user_name")
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