package com.daya.githubuser.data.profile.datasource

import android.content.res.Resources
import com.daya.githubuser.data.profile.general.FollowersFollowing
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.data.profile.network.NetWorkBio
import com.daya.githubuser.di.GithubUserApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface DetailBioDataSource {
    suspend fun getDetailBio(userName : String) : GeneralBio

    suspend fun getListFollowers(userName: String) : List<FollowersFollowing>

     suspend fun getListFollowing(userName: String) : List<FollowersFollowing>
}

class NetworkDetailBioDataSource
@Inject
constructor(
        private val githubUserApiService: GithubUserApiService,
) : DetailBioDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getDetailBio(userName : String): GeneralBio = suspendCancellableCoroutine { continuation ->
        val client = githubUserApiService.getDetailUsers(userName)

        client.enqueue(object : Callback<NetWorkBio>{
            override fun onResponse(call: Call<NetWorkBio>, response: Response<NetWorkBio>) {
                val netBio = response.body()
                if (netBio != null) {
                    val generalBio =  GeneralBio(
                                username = netBio.login,
                                followersUrl = netBio.followers_url,
                                followingUrl = netBio.following_url,
                                avatar =  netBio.avatar_url,
                                name = netBio.name,
                                location = netBio.location,
                                repoCount = netBio.public_repos,
                                followingCount = netBio.following,
                                followerCount = netBio.followers,
                                company = netBio.company
                        )
                    continuation.resume(generalBio)
                } else {
                    continuation.resumeWithException(NoSuchElementException("response.body is null, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<NetWorkBio>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

       continuation.invokeOnCancellation {
           client.cancel()
       }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getListFollowers(userName: String): List<FollowersFollowing> = suspendCancellableCoroutine { continuation ->
        val client = githubUserApiService.getUserFollowers(userName)

        client.enqueue(object : Callback<List<NetWorkBio>> {
            override fun onResponse(
                call: Call<List<NetWorkBio>>,
                response: Response<List<NetWorkBio>>
            ) {
                val netListFollowers = response.body()

                if (netListFollowers != null) {

                    val listFollowers = netListFollowers.asSequence()
                        .map { nonNullBio ->
                            FollowersFollowing(
                                followUsername = nonNullBio.login,
                                avatarUrl = nonNullBio.avatar_url
                                )
                        }.toList()

                    continuation.resume(listFollowers)
                } else {
                    continuation.resumeWithException(Resources.NotFoundException("response.body is null, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<NetWorkBio>>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
        continuation.invokeOnCancellation {
            client.cancel()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getListFollowing(userName: String): List<FollowersFollowing> = suspendCancellableCoroutine {continuation ->
        val client = githubUserApiService.getUserFollowing(userName)

        client.enqueue(object : Callback<List<NetWorkBio>> {
            override fun onResponse(
                call: Call<List<NetWorkBio>>,
                response: Response<List<NetWorkBio>>
            ) {
                val netListFollowers = response.body()

                if (netListFollowers != null) {

                    val listFollowers =  netListFollowers.asSequence()
                        .map { nonNullBio ->
                            FollowersFollowing(
                                followUsername = nonNullBio.login,
                                avatarUrl = nonNullBio.avatar_url
                            )
                        }.toList()

                    continuation.resume(listFollowers)

                } else {
                    continuation.resumeWithException(Resources.NotFoundException(("response.body is null, ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<List<NetWorkBio>>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }


}
