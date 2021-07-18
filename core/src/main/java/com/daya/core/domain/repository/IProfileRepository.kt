package com.daya.core.domain.repository

import com.daya.core.data.di.bio.BioEntity
import com.daya.core.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.core.data.di.bio.FollowersEntity
import com.daya.core.data.di.bio.FollowingEntity
import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.domain.model.GeneralBio
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {
    suspend fun getListBioFromJson(): List<GeneralBio>
    suspend fun searchUsers(userName: String): List<GeneralBio>
    suspend fun getDetailBioFromNetwork(userName: String): GeneralBio

    suspend fun getListFollowersFromNetwork(userName: String): List<FollowersFollowing>
    suspend fun getListFollowingFromNetwork(userName: String): List<FollowersFollowing>

    suspend fun addUserFavorite(bioEntity: BioEntity): Long
    suspend fun insertfollowers(followers : List<FollowersEntity>)
    suspend fun insertFollowing(following : List<FollowingEntity>)

    suspend fun getCurrentUser(userName: String): GeneralBio?
    suspend fun observeIsUserFavorite(userName: String): Boolean
    suspend fun removeUserFavorite(userName: String)
    fun getAllFavoriteEntity(): Flow<List<BioEntityWithFollowersFollowing>>
}