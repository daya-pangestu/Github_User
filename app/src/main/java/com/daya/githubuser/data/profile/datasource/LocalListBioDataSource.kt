package com.daya.githubuser.data.profile.datasource

import android.content.Context
import com.daya.githubuser.R
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.data.profile.local.LocalBio
import com.daya.githubuser.data.profile.local.LocalUsers
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocalListBioDataSource
@Inject
constructor(
    @ApplicationContext
    val context: Context,
    private val moshi: Moshi
) : ListBioDataSource{
    override suspend fun getListBio(queryName : String): List<GeneralBio> = suspendCancellableCoroutine { continuation ->
        try {
            val itemListJsonString : String = context.resources.openRawResource(R.raw.githubuser).bufferedReader().use { it.readText() }

            val jsonAdapter = moshi.adapter(LocalUsers::class.java)
            val listProfile : LocalUsers = jsonAdapter.fromJson(itemListJsonString)!! //it should be Caught if error thrown

            val listBioWithResDrawableAvatar = listProfile.users.sanitizeListBio()

            continuation.resume(listBioWithResDrawableAvatar)
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private fun List<LocalBio>.sanitizeListBio(): List<GeneralBio> {
        return this.map{ localBio ->
            val sanitizeAvaName = localBio.avatar.avatarToResDrawable()
            GeneralBio(
                avatar = sanitizeAvaName,
                company = localBio.company,
                followerCount = localBio.follower,
                followingCount = localBio.following,
                repoCount = localBio.repository,
                name = localBio.name,
                location = localBio.location,
                username = localBio.username
            )
        }
    }

    private fun String.avatarToResDrawable(): String {
        val sanitizeAvaName = this.removePrefix("@drawable/")
        val resDrawableId =
            context.resources.getIdentifier(sanitizeAvaName, "drawable", context.packageName)
        val resDrawable = resDrawableId.toString()

        return "$resDrawable-$this"
    }
}