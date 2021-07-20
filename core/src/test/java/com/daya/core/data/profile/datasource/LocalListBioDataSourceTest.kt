package com.daya.core.data.profile.datasource

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalListBioDataSourceTest {

    val context = ApplicationProvider.getApplicationContext<Context>()
    val moshi = Moshi.Builder().build()

    lateinit var localListBioDataSource: LocalListBioDataSource


    @Before
    fun setUp() {
        localListBioDataSource = LocalListBioDataSource(context,moshi)
    }

    @Test
    fun `LocalListBioDataSource#getListBio should return list generalbio from raw folder`() = runBlocking {
        val result = localListBioDataSource.getListBio()

        assertThat(result.size).isEqualTo(10)
        assertThat(result[0].username).isEqualTo("JakeWharton")
        assertThat(result[0].name).isEqualTo("Jake Wharton")
        assertThat(result[0].avatar).isEqualTo("https://avatars.githubusercontent.com/u/66577?v=4")
        assertThat(result[0].location).isEqualTo("Pittsburgh, PA, USA")
        assertThat(result[0].company).isEqualTo("Google, Inc.")

    }
}