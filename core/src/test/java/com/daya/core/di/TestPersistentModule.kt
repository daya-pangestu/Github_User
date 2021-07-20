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
import dagger.hilt.testing.TestInstallIn
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@TestInstallIn(
    replaces = [PersistentModule::class],
    components = [SingletonComponent::class]
)
@Module
object TestPersistentModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase {
        return Room
            .inMemoryDatabaseBuilder(context,GithubUserDatabase::class.java)
            .build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(db: GithubUserDatabase): ProfileDao {
        return db.profileDao()
    }

}