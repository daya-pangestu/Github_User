package com.daya.core.data.profile.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.core.data.profile.network.NetWorkUsers
import com.daya.core.di.detail.NetWorkDetailBio
import com.daya.core.faker.FakeDataClasses
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NetWorkListBioDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var moshi : Moshi

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Inject
    lateinit var netWorkListBioDataSource: NetWorkListBioDataSource

    @Test
    fun `NetWorkListBioDataSource#getListBio should return list generalBio`() = runBlocking {
        val expectedResponse = FakeDataClasses.networkUsers
        val moshiAdapter = moshi.adapter(NetWorkUsers::class.java)
        val expectedJsonResponse = moshiAdapter.toJson(expectedResponse)

        val mockResponse = MockResponse()
            .setBody(expectedJsonResponse)

        mockWebServer.enqueue(mockResponse)

        val actualResponse = netWorkListBioDataSource.getListBio("something")

        assertThat(expectedResponse.listNetWorkBio.size).isEqualTo(actualResponse.size)

        assertThat(expectedResponse.listNetWorkBio[0].name).isEqualTo(actualResponse[0].name)
        assertThat(expectedResponse.listNetWorkBio[0].avatar_url).isEqualTo(actualResponse[0].avatar)
        assertThat(expectedResponse.listNetWorkBio[0].company).isEqualTo(actualResponse[0].company)
        assertThat(expectedResponse.listNetWorkBio[0].location).isEqualTo(actualResponse[0].location)
        assertThat(expectedResponse.listNetWorkBio[0].public_repos).isEqualTo(actualResponse[0].repoCount)

    }
}