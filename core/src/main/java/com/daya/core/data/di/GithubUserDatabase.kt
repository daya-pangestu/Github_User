package com.daya.core.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.core.data.di.bio.BioEntity
import com.daya.core.data.di.bio.FollowersEntity
import com.daya.core.data.di.bio.FollowingEntity

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