package com.daya.githubuser.data.profile.datasource

import com.daya.githubuser.data.di.dao.bio.ProfileDao
import com.daya.githubuser.data.profile.general.FollowersFollowing
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.xtension.toFollowersFollowing
import com.daya.githubuser.xtension.toGeneralBio
import javax.inject.Inject

class LocalDetailFavoriteDataSource
@Inject
constructor(
        private val profileDao: ProfileDao
) : DetailBioDataSource {

    override suspend fun getDetailBio(userName: String): GeneralBio {
        val user = profileDao.getUserFavorite(userName)

        val following = profileDao.getFollowing(userName)

        val followers = profileDao.getFollowers(userName)


        val generalbio = user?.toGeneralBio().apply {
                this?.followers = followers.toFollowersFollowing()
                this?.followings = following.toFollowersFollowing()
        }

        return generalbio!!
    }

    override suspend fun getListFollowers(userName: String): List<FollowersFollowing> {
        val followersEntity = profileDao.getFollowers(userName)
        return followersEntity.toFollowersFollowing()
    }

    override suspend fun getListFollowing(userName: String): List<FollowersFollowing> {
        val followingEntity = profileDao.getFollowing(userName)
        return followingEntity.toFollowersFollowing()
    }
}
