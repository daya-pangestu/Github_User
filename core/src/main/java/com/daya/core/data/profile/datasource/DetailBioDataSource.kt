package com.daya.core.data.profile.datasource

import android.content.res.Resources
import com.daya.core.data.di.ProfileDao
import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.domain.model.GeneralBio
import com.daya.core.data.profile.network.NetWorkBio
import com.daya.core.di.GithubUserApiService
import com.daya.core.utils.toFollowersFollowing
import com.daya.core.utils.toGeneralBio
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
                    continuation.resumeWithException(NoSuchElementException("${response.errorBody()?.string()}"))
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
                    continuation.resumeWithException(Resources.NotFoundException("${response.errorBody()?.string()}"))
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

    override suspend fun getListFollowing(userName: String): List<FollowersFollowing> = suspendCancellableCoroutine { continuation ->
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
                    continuation.resumeWithException(Resources.NotFoundException(("${response.errorBody()?.string()}")))
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

class LocalDetailFavoriteDataSource
@Inject
constructor(
    private val profileDao: ProfileDao
) : DetailBioDataSource {

    override suspend fun getDetailBio(userName: String): GeneralBio {
        val user = profileDao.getUserFavorite(userName)
        val generalbio = user?.toGeneralBio()
        return generalbio!!
    }

    override suspend fun getListFollowers(userName: String): List<FollowersFollowing> {
        val followersEntity = profileDao.getFollowers(userName)
        return followersEntity.toFollowersFollowing()
    }

    override suspend fun getListFollowing(userName: String): List<FollowersFollowing> {
        val followingEntity = profileDao.getFollowing(userName)
        return followingEntity.toFollowersFollowing()
    }
}

