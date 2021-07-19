package com.daya.core.di

import android.content.Context
import androidx.room.Room
import com.daya.core.data.di.GithubUserDatabase
import com.daya.core.data.di.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PersistentModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("githubuser".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room
            .databaseBuilder(
                context,
                GithubUserDatabase::class.java,
                "github_user.db"
            )
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(db : GithubUserDatabase): ProfileDao {
        return db.profileDao()
    }

}