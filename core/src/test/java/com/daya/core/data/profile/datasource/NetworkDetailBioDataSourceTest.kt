package com.daya.core.data.profile.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.core.data.profile.network.NetWorkBio
import com.daya.core.di.detail.NetWorkDetailBio
import com.daya.core.faker.FakeDataClasses
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NetworkDetailBioDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var moshi: Moshi

    @Inject
    @NetWorkDetailBio
    lateinit var networkDetailBioDataSource : DetailBioDataSource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `networkGetDetailBio should return list generalBio`() = runBlocking {
        val expectedResponse = FakeDataClasses.networkBio[0]
        val moshiAdapter = moshi.adapter(NetWorkBio::class.java)
        val expectedJsonResponse = moshiAdapter.toJson(expectedResponse)

        val mockResponse = MockResponse()
            .setBody(expectedJsonResponse)

        mockWebServer.enqueue(mockResponse)

        val actualResponse = networkDetailBioDataSource.getDetailBio(expectedResponse.name)

        assertThat(actualResponse.name).isEqualTo(expectedResponse.name)
        assertThat(actualResponse.avatar).isEqualTo(expectedResponse.avatar_url)
        assertThat(actualResponse.company).isEqualTo(expectedResponse.company)
        assertThat(actualResponse.location).isEqualTo(expectedResponse.location)
        assertThat(actualResponse.repoCount).isEqualTo(expectedResponse.public_repos)

    }
}