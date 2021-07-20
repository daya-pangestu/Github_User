package com.daya.core.data.profile.datasource

import com.daya.core.data.di.ProfileDao
import com.daya.core.faker.FakeDataClasses
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LocalDetailFavoriteDataSourceTest  {

    val profileDao: ProfileDao = mock()

    lateinit var localDetailFavoriteDataSource: LocalDetailFavoriteDataSource

    @Before
    fun setUp() {
        localDetailFavoriteDataSource = LocalDetailFavoriteDataSource(profileDao)
    }

    @Test
    fun `localGetDetailBio should return generalBio`() = runBlocking {
        val expectedBioWithFollow = FakeDataClasses.bioEntityWithFollowersFollowing
        whenever(profileDao.getUserFavorite(expectedBioWithFollow.bio.username)).thenReturn(expectedBioWithFollow)
        
        val actualBio = localDetailFavoriteDataSource.getDetailBio(expectedBioWithFollow.bio.username)

        assertThat(expectedBioWithFollow.bio.name).isEqualTo(actualBio.name)
        assertThat(expectedBioWithFollow.bio.username).isEqualTo(actualBio.username)
        assertThat(expectedBioWithFollow.bio.repoCount).isEqualTo(actualBio.repoCount)
        assertThat(expectedBioWithFollow.bio.avatar).isEqualTo(actualBio.avatar)
        assertThat(expectedBioWithFollow.bio.location).isEqualTo(actualBio.location)
        assertThat(expectedBioWithFollow.bio.company).isEqualTo(actualBio.company)
    }
}