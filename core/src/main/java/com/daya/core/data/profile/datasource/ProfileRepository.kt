
package com.daya.core.data.profile.datasource

import com.daya.core.data.di.bio.BioEntity
import com.daya.core.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.core.data.di.bio.FollowersEntity
import com.daya.core.data.di.bio.FollowingEntity
import com.daya.core.data.di.ProfileDao
import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.detail.NetWorkDetailBio
import com.daya.core.di.profile.JsonProfile
import com.daya.core.di.profile.NetWorkProfile
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.utils.toGeneralBio
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository
@Inject
constructor(
        @JsonProfile private val jsonProfileDataSourceList: ListBioDataSource,
        @NetWorkProfile private val netWorkListBioDataSource: ListBioDataSource,
        @NetWorkDetailBio private val networkDetailBioDataSource: DetailBioDataSource,
        private val profileDao: ProfileDao
) : IProfileRepository {
    override suspend fun getListBioFromJson(): List<GeneralBio> {
        return jsonProfileDataSourceList.getListBio()
    }

    override suspend fun searchUsers(userName: String): List<GeneralBio> {
        return netWorkListBioDataSource.getListBio(userName)
    }

     override suspend fun getDetailBioFromNetwork(userName: String): GeneralBio {
        val flowBio = networkDetailBioDataSource.getDetailBio(userName)

        return flowBio
    }

     override suspend fun getListFollowersFromNetwork(userName: String): List<FollowersFollowing> {
         return networkDetailBioDataSource.getListFollowers(userName)
    }

     override suspend fun getListFollowingFromNetwork(userName: String): List<FollowersFollowing> {
        return networkDetailBioDataSource.getListFollowing(userName)
    }

    override suspend fun addUserFavorite(bioEntity: BioEntity): Long {
        return profileDao.addFaforiteUserBio(bioEntity)
    }

    override suspend fun insertfollowers(followers : List<FollowersEntity>) {
        profileDao.insertFollowers(followers)
    }

    override suspend fun insertFollowing(following : List<FollowingEntity>) {
        profileDao.insertFollowing(following)
    }

    override suspend fun getCurrentUser(userName: String): GeneralBio? {
        return profileDao.getUserFavorite(userName)?.toGeneralBio()
    }

    override suspend fun observeIsUserFavorite(userName: String): Boolean {
        val currentUser = profileDao.getUserFavorite(userName)
        return currentUser != null
    }

    override suspend fun removeUserFavorite(userName: String) {
        profileDao.deleteFaforiteUserBio(userName)
    }

    override fun getAllFavoriteEntity(): Flow<List<BioEntityWithFollowersFollowing>> {
        return profileDao.getFavoriteUserBioWithFollowersFollowing().distinctUntilChanged()
    }
}