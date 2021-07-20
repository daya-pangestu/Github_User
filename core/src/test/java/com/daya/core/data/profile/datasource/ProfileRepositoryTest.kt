package com.daya.core.data.profile.datasource

import com.daya.core.data.di.ProfileDao
import com.daya.core.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.faker.FakeDataClasses
import com.daya.core.utils.toGeneralBio
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

class ProfileRepositoryTest {

    private val jsonProfileDataSourceList: LocalListBioDataSource = mock()
    private val netWorkListBioDataSource: NetWorkListBioDataSource = mock()
    private val networkDetailBioDataSource: NetworkDetailBioDataSource = mock()
    private val profileDao : ProfileDao = mock()

    private val dummyBioEntity = FakeDataClasses.entityBio.first()
    private val dummyListGeneralBio = FakeDataClasses.listGeneralBio
    private val dummyGeneralBio = dummyListGeneralBio.first()
    private val dummyBioEntityWithFollowersFollowing = BioEntityWithFollowersFollowing(
        bio = dummyBioEntity,
        following = emptyList(),
        followers = emptyList()
    )

    lateinit var profileRepository: IProfileRepository

    @Before
    fun setUp() {
        profileRepository = ProfileRepository(jsonProfileDataSourceList,netWorkListBioDataSource,networkDetailBioDataSource,profileDao)
    }

    @Test
    fun `verify profileRepository#getlistBioFromJson get called`() {
        runBlocking {
            whenever(jsonProfileDataSourceList.getListBio()).thenReturn(dummyListGeneralBio)
            val actualListBio = profileRepository.getListBioFromJson()

            verify(jsonProfileDataSourceList).getListBio()
            assertThat(actualListBio).isEqualTo(dummyListGeneralBio)
        }
    }
    @Test
    fun `verify profileRepository#searchUsers get called`() {
        runBlocking {
            whenever(netWorkListBioDataSource.getListBio(dummyGeneralBio.username)).thenReturn(dummyListGeneralBio)
            val actualListBio = profileRepository.searchUsers(dummyGeneralBio.username)

            verify(netWorkListBioDataSource).getListBio(dummyGeneralBio.username)
            assertThat(actualListBio).isEqualTo(dummyListGeneralBio)
        }
    }

    @Test
    fun  `verify profileRepository#getDetailBioFromNetwork get called`() {
        runBlocking{
            whenever(networkDetailBioDataSource.getDetailBio(dummyGeneralBio.username)).thenReturn(dummyGeneralBio)
            val actualGeneralBio = profileRepository.getDetailBioFromNetwork(dummyGeneralBio.username)

            verify(networkDetailBioDataSource).getDetailBio(dummyGeneralBio.username)
            assertThat(actualGeneralBio).isEqualTo(dummyGeneralBio)

        }
    }

    @Test
    fun `verify profileDao#addFaforiteUserBio get called`() {
        runBlocking{
            val expectedRowId = 1L
            whenever(profileDao.addFaforiteUserBio(dummyBioEntity)).thenReturn(expectedRowId)
            val actualRowId = profileRepository.addUserFavorite(dummyBioEntity)

            verify(profileDao).addFaforiteUserBio(dummyBioEntity)
            assertThat(actualRowId).isEqualTo(expectedRowId)
        }
    }

    @Test
    fun `verify profileDao#getUserFavorite get called`() {
        runBlocking {
            val expectedGeneralBio = dummyBioEntityWithFollowersFollowing.toGeneralBio()
            whenever(profileDao.getUserFavorite(dummyBioEntity.username)).thenReturn(dummyBioEntityWithFollowersFollowing)
            val actualGeneralBio = profileRepository.getCurrentUser(dummyBioEntity.username)

            verify(profileDao).getUserFavorite(dummyBioEntity.username)
            assertThat(actualGeneralBio).isEqualTo(expectedGeneralBio)
        }
    }

    @Test
    fun `verify profileDao#observeIsUserFavorite can return null`() {
        runBlocking {
            whenever(profileDao.getUserFavorite(dummyBioEntity.username)).thenReturn(null)
            val isUserFavorite = profileRepository.observeIsUserFavorite(dummyBioEntity.username)

            verify(profileDao).getUserFavorite(dummyBioEntity.username)
            assertThat(isUserFavorite).isEqualTo(false)
        }
    }

    @Test
    fun `verify profileDao#deleteFaforiteUserBio get called`() {
        runBlocking {
            profileRepository.removeUserFavorite(dummyBioEntity.username)
            verify(profileDao).deleteFaforiteUserBio(dummyBioEntity.username)
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `verify profileDao#getFavoriteUserBioWithFollowersFollowing get called`() {
        runBlocking {
            val flowListBioWithFollow = flow<List<BioEntityWithFollowersFollowing>> {listOf(dummyBioEntityWithFollowersFollowing)}
            whenever(profileDao.getFavoriteUserBioWithFollowersFollowing()).thenReturn(flowListBioWithFollow)
            val actual = profileRepository.getAllFavoriteEntity()
            verify(profileDao).getFavoriteUserBioWithFollowersFollowing()
            assertThat(actual).isNotNull()
        }
    }
}