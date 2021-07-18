package com.daya.githubuser.presentation.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.daya.core.data.di.GithubUserDatabase
import com.daya.core.data.di.ProfileDao
import com.daya.githubuser.presentation.contentprovider.ProviderContract.AUTHORITY
import com.daya.githubuser.presentation.contentprovider.ProviderContract.CONTENT_URI
import com.daya.githubuser.presentation.contentprovider.ProviderContract.TABLE_NAME
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class GitUserContentProvider : ContentProvider() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GitHubUserContentProviderEntryPoint{
        fun  profileDao() : ProfileDao
        fun githubUserDb() : GithubUserDatabase
    }

    companion object {
        private const val BIO = 1
        private const val BIO_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        //TODO inject db with hilt
        private lateinit var profileDao: ProfileDao
        private lateinit var db : GithubUserDatabase

        init {
            // content://com.daya.githubuser.mynotesapp/bioEntity
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, BIO)
            // content://com.daya.githubuser.mynotesapp/bioEntity/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", BIO_ID)
        }
    }

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoist = EntryPointAccessors.fromApplication(
            appContext,
            GitHubUserContentProviderEntryPoint::class.java
        )
        profileDao = hiltEntryPoist.profileDao()
        db = hiltEntryPoist.githubUserDb()

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            BIO -> {
                db.openHelper.writableDatabase.query(
                    SupportSQLiteQueryBuilder.builder(TABLE_NAME)
                        .selection(selection, selectionArgs)
                        .columns(projection)
                        .orderBy(sortOrder)
                        .create()
                ) //list of bio
            }
            BIO_ID -> {
                db.openHelper.writableDatabase.query(SupportSQLiteQueryBuilder
                    .builder(TABLE_NAME)
                    .selection(selection,selectionArgs)
                    .create())
            } //current bio
            else -> null
        }
    }


    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (BIO) {
            sUriMatcher.match(uri) -> {
                db.openHelper.writableDatabase.insert(TABLE_NAME,SQLiteDatabase.CONFLICT_REPLACE,values)
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (BIO_ID) {
            sUriMatcher.match(uri) -> {
                db.openHelper.writableDatabase.update(TABLE_NAME,SQLiteDatabase.CONFLICT_REPLACE,values,selection,selectionArgs)
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (BIO_ID) {
            sUriMatcher.match(uri) -> {
                db.openHelper.writableDatabase.delete(TABLE_NAME,selection,selectionArgs)
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }


}