package com.daya.core.di

import com.daya.core.utils.NullAbleStringFieldAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    replaces = [NetWorkModule::class],
    components = [SingletonComponent::class]
)
object TestNetWorkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NullAbleStringFieldAdapter)
        .build()


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {

        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubUserClient(baseUrl : HttpUrl,moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(client: Retrofit): GithubUserApiService {
        return client.create(GithubUserApiService::class.java)
    }

    @Provides
    fun provideBaseUrl(mockWebServer: MockWebServer): HttpUrl {
        return mockWebServer.url("/")
    }

    @Provides
    @Singleton
    fun provideMockWebserver() = MockWebServer()

}