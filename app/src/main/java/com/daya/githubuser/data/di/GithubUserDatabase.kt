package com.daya.githubuser.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.githubuser.data.di.bio.BioEntity
import com.daya.githubuser.data.di.bio.FollowersEntity
import com.daya.githubuser.data.di.bio.FollowingEntity

@Database(
    entities = [
        BioEntity::class,
        FollowersEntity::class,
        FollowingEntity::class
    ],
    version = 2,
    exportSchema = false,
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

}