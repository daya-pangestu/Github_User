package com.daya.core.data.profile.datasource

import android.content.Context
import com.daya.core.R
import com.daya.core.data.profile.network.NetWorkUsers
import com.daya.core.data.profile.resourceasset.ResourceBio
import com.daya.core.data.profile.resourceasset.ResourceUsers
import com.daya.core.di.GithubUserApiService
import com.daya.core.domain.model.GeneralBio
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


interface ListBioDataSource {
   suspend fun getListBio(queryName : String = "") : List<GeneralBio>
}

class NetWorkListBioDataSource
@Inject
constructor(
   private val githubUserApiService: GithubUserApiService
) : ListBioDataSource {

   override suspend fun getListBio(queryName: String): List<GeneralBio> = suspendCancellableCoroutine { continuation ->

      if (queryName.isEmpty() || queryName.isBlank()) {
         continuation.resume(emptyList())
         return@suspendCancellableCoroutine
      }

      val client = githubUserApiService.searchUsers(queryName)
      client.enqueue(object : Callback<NetWorkUsers> {
         override fun onResponse(call: Call<NetWorkUsers>, response: Response<NetWorkUsers>) {

            val listNetBio = response.body()?.listNetWorkBio

            if (listNetBio != null) {
               val listGeneralBio = listNetBio.asSequence().map { nonNullableBio ->
                  GeneralBio(
                     username = nonNullableBio.login,
                     followersUrl = nonNullableBio.followers_url,
                     followingUrl = nonNullableBio.following_url,
                     avatar =  nonNullableBio.avatar_url,
                     name = nonNullableBio.name,
                     location = nonNullableBio.location,
                     repoCount = nonNullableBio.public_repos,
                     followingCount = nonNullableBio.following,
                     followerCount = nonNullableBio.followers,
                     company = nonNullableBio.company
                  )
               }.toList()

               continuation.resume(listGeneralBio)

            } else {
               continuation.resumeWithException(Throwable("response.body is null, ${response.message()}"))
            }
         }
         override fun onFailure(call: Call<NetWorkUsers>, t: Throwable) {
            continuation.resumeWithException(t)
         }
      })
      continuation.invokeOnCancellation {
         client.cancel()
      }
   }
}

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

         val jsonAdapter = moshi.adapter(ResourceUsers::class.java)
         val listProfile : ResourceUsers = jsonAdapter.fromJson(itemListJsonString)!! //it should be Caught if error thrown

         val listBioWithResDrawableAvatar = listProfile.users.sanitizeListBio()

         continuation.resume(listBioWithResDrawableAvatar)
      } catch (e: Exception) {
         continuation.resumeWithException(e)
      }
   }

   private fun List<ResourceBio>.sanitizeListBio(): List<GeneralBio> {
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