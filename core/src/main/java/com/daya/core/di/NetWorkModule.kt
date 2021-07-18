package com.daya.core.di

import com.daya.core.data.profile.network.NetWorkBio
import com.daya.core.data.profile.network.NetWorkUsers
import com.daya.core.utils.NullAbleStringFieldAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NullAbleStringFieldAdapter)
        .build()

    @Provides
    @Singleton
    fun provideGithubUserClient(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(client: Retrofit): GithubUserApiService {
        return client.create(GithubUserApiService::class.java)
    }
}

interface GithubUserApiService {

    @GET("/search/users")
    fun searchUsers(
            @Query("q") query: String,
    ): Call<NetWorkUsers>

    @GET("/users/{username}")
    fun getDetailUsers(
            @Path("username") username : String
    ) : Call<NetWorkBio>

    @GET("/users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username : String,
        @Path("per_page") per_page : Int = 4 //limit result from api, to avoid rate limit
    ) : Call<List<NetWorkBio>>

    @GET("/users/{username}/following")
    fun getUserFollowing(
        @Path("username") username : String,
        @Path("per_page") per_page : Int = 4
    ) : Call<List<NetWorkBio>>
}