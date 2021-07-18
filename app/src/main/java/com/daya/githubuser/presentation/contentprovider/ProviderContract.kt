package com.daya.githubuser.presentation.contentprovider

import android.net.Uri

object ProviderContract {
    const val AUTHORITY = "com.daya.githubusertt"
    private const val SCHEME = "content"

    const val TABLE_NAME = "bioEntity"
    object BioEntity{
        const val USERNAME = "username"
        const val AVATAR = "avatar"
        const val COMPANY = "company"
        const val FOLLOWERCOUNT = "followerCount"
        const val FOLLOWINGCOUNT = "followingCount"
        const val LOCATION = "location"
        const val NAME = "name"
        const val REPOCOUNT = "repoCount"
        const val FOLLOWERSURL = "followersUrl"
        const val FOLLOWINGURL = "followingUrl"
    }

    // URI content://com.daya.githubuser/bioEntity
    val CONTENT_URI: Uri = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}