package com.daya.consumerapp.contentprovider

import android.net.Uri

object ProviderContract {
    private const val AUTHORITY = "com.daya.githubusertt"
    private const val SCHEME = "content"

    private const val TABLE_NAME = "bioEntity"
    object BioEntity{
        const val BIO_USER_NAME = "bio_user_name"
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