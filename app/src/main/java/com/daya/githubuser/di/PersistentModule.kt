package com.daya.githubuser.di

import android.content.Context
import androidx.room.Room
import com.daya.githubuser.data.di.GithubUserDatabase
import com.daya.githubuser.data.di.dao.bio.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PersistentModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase {
        return Room
            .databaseBuilder(
                context,
                GithubUserDatabase::class.java,
                "github_user.db"
            ).build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(db : GithubUserDatabase): ProfileDao {
        return db.profileDao()
    }

}