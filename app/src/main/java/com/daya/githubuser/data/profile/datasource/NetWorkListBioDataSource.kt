package com.daya.githubuser.data.profile.datasource

import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.data.profile.network.NetWorkUsers
import com.daya.githubuser.di.GithubUserApiService
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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