
package com.daya.githubuser.data.profile.datasource

import com.daya.githubuser.data.di.bio.BioEntity
import com.daya.githubuser.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.githubuser.data.di.bio.FollowersEntity
import com.daya.githubuser.data.di.bio.FollowingEntity
import com.daya.githubuser.data.di.dao.bio.ProfileDao
import com.daya.githubuser.data.profile.general.FollowersFollowing
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.di.detail.NetWorkDetailBio
import com.daya.githubuser.di.profile.JsonProfile
import com.daya.githubuser.di.profile.NetWorkProfile
import com.daya.githubuser.xtension.toGeneralBio
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

//TODO make profile repository scoped to viewModel
@Singleton
class ProfileRepository
@Inject
constructor(
        @JsonProfile private val jsonProfileDataSourceList: ListBioDataSource,
        @NetWorkProfile private val netWorkListBioDataSource: ListBioDataSource,
        @NetWorkDetailBio private val networkDetailBioDataSource: DetailBioDataSource,
        private val profileDao: ProfileDao
) {
    suspend fun getListBioFromJson(): List<GeneralBio> {
        return jsonProfileDataSourceList.getListBio()
    }

    suspend fun searchUsers(userName: String): List<GeneralBio> {
        return netWorkListBioDataSource.getListBio(userName)
    }

     suspend fun getDetailBioFromNetwork(userName: String): GeneralBio {
        val flowBio = networkDetailBioDataSource.getDetailBio(userName)
        val flowListFollowers = getListFollowersFromNetwork(userName)
        val flowListFollowing = getListFollowingFromNetwork(userName)

        return flowBio.apply {
            followers = flowListFollowers
            followings = flowListFollowing
        }
    }

     suspend fun getListFollowersFromNetwork(userName: String): List<FollowersFollowing> {
         return networkDetailBioDataSource.getListFollowers(userName)
    }

     suspend fun getListFollowingFromNetwork(userName: String): List<FollowersFollowing> {
        return networkDetailBioDataSource.getListFollowing(userName)
    }

    suspend fun addUserFavorite(bioEntity: BioEntity): Long {
        return profileDao.addFaforiteUserBio(bioEntity)
    }

    suspend fun insertfollowers(followers : List<FollowersEntity>) {
        profileDao.insertFollowers(followers)
    }

    suspend fun insertFollowing(following : List<FollowingEntity>) {
        profileDao.insertFollowing(following)
    }

    suspend fun getCurrentUser(userName: String): GeneralBio? {
        return profileDao.getUserFavorite(userName)?.toGeneralBio()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun observeIsUserFavorite(userName: String): Boolean {
        val currentUser = profileDao.getUserFavorite(userName)
        return currentUser != null
    }

    suspend fun removeUserFavorite(userName: String) {
        profileDao.deleteFaforiteUserBio(userName)
    }

    fun getAllFavoriteEntity(): Flow<List<BioEntityWithFollowersFollowing>> {
        return profileDao.getFavoriteUserBioWithFollowersFollowing().distinctUntilChanged()
    }
}