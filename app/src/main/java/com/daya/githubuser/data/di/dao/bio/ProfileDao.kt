package com.daya.githubuser.data.di.dao.bio

import androidx.room.*
import com.daya.githubuser.data.di.bio.BioEntity
import com.daya.githubuser.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.githubuser.data.di.bio.FollowersEntity
import com.daya.githubuser.data.di.bio.FollowingEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProfileDao {

 @Transaction
 @Query("SELECT * FROM BioEntity")
 abstract fun getFavoriteUserBioWithFollowersFollowing(): Flow<List<BioEntityWithFollowersFollowing>>

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 abstract suspend fun addFaforiteUserBio(bioEntity: BioEntity): Long

 @Transaction
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 abstract suspend fun insertFollowers(followers: List<FollowersEntity>)

 @Transaction
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 abstract suspend fun insertFollowing(followings: List<FollowingEntity>)

 @Query("DELETE FROM bioentity WHERE bio_user_name = :userName")
 abstract suspend fun deleteFaforiteUserBio(userName: String)

 @Transaction
 @Query("SELECT * FROM bioentity WHERE bio_user_name = :userName")
 abstract suspend fun getUserFavorite(userName: String): BioEntityWithFollowersFollowing?

 @Query("SELECT * FROM followersentity WHERE follower_user_name = :userName")
 abstract suspend fun getFollowers(userName: String): List<FollowersEntity>

 @Query("SELECT * FROM followingentity WHERE following_user_name = :userName")
 abstract suspend fun getFollowing(userName: String): List<FollowingEntity>


}