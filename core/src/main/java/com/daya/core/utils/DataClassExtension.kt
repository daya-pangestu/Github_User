package com.daya.core.utils

import com.daya.core.data.di.bio.BioEntity
import com.daya.core.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.core.data.di.bio.FollowersEntity
import com.daya.core.data.di.bio.FollowingEntity
import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.domain.model.GeneralBio

//bio
fun BioEntityWithFollowersFollowing.toGeneralBio(): GeneralBio {
    return GeneralBio(
            avatar = bio.avatar,
            company = bio.company,
            followerCount = bio.followerCount,
            followingCount = bio.followingCount,
            location = bio.location,
            name = bio.name,
            repoCount = bio.repoCount,
            username = bio.username, // or login
            followersUrl = bio.followersUrl,
            followingUrl = bio.followingUrl,
            followers = followers.toFollowersFollowing(),
            followings = following.toFollowersFollowing()
    )
}

fun GeneralBio.toBioEntity(): BioEntity {
    return BioEntity(
            avatar = avatar,
            company = company,
            followerCount = followerCount,
            followingCount = followingCount,
            location = location,
            name = name,
            repoCount = repoCount,
            username = username, // or login
            followersUrl = followersUrl,
            followingUrl = followingUrl,
    )
}

fun GeneralBio.toFollowersEntity(): List<FollowersEntity> {
    return this.followers.map {
        FollowersEntity(
                followersUserName = it.followUsername,
                avatarUrl = it.avatarUrl,
                userName = this.username
        )
    }
}

fun GeneralBio.toFollowingEntity(): List<FollowingEntity> {
    return this.followings.asSequence().map {
        FollowingEntity(
                followingUserName = it.followUsername,
                avatarUrl = it.avatarUrl,
                userName = this.username
        )
    }.toList()
}

@JvmName("fromFollowEntititytoFollowersFollowing")
fun List<FollowersEntity>.toFollowersFollowing(): List<FollowersFollowing> {
    return this.asSequence().map {
        FollowersFollowing(
                followUsername = it.followersUserName,
                avatarUrl = it.avatarUrl,
        )
    }.toList()
}

@JvmName("fromFollowingEntityToFollowersFollowing")
fun List<FollowingEntity>.toFollowersFollowing(): List<FollowersFollowing> {
    return this.asSequence().map {
        FollowersFollowing(
                followUsername = it.followingUserName,
                avatarUrl = it.avatarUrl,
        )
    }.toList()
}