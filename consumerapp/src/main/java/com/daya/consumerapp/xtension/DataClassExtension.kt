package com.daya.consumerapp.xtension

import android.database.Cursor
import com.daya.consumerapp.contentprovider.ProviderContract
import com.daya.consumerapp.data.bio.FollowingEntity
import com.daya.consumerapp.data.general.FollowersFollowing
import com.daya.consumerapp.data.general.GeneralBio

@JvmName("fromFollowingEntityToFollowersFollowing")
fun List<FollowingEntity>.toFollowersFollowing(): List<FollowersFollowing> {
    return this.asSequence().map {
        FollowersFollowing(
                followUsername = it.followingUserName,
                avatarUrl = it.avatarUrl,
        )
    }.toList()
}


fun Cursor.toListGeneralBio(): List<GeneralBio> {
    val listGeneralBio = mutableListOf<GeneralBio>()

    this.apply {
        while (moveToNext()) {
            val username = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.BIO_USER_NAME))
            val avatar = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.AVATAR))
            val company = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.COMPANY))
            val followerCount = getInt(getColumnIndexOrThrow(ProviderContract.BioEntity.FOLLOWERCOUNT))
            val followingCount = getInt(getColumnIndexOrThrow(ProviderContract.BioEntity.FOLLOWINGCOUNT))
            val location = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.LOCATION))
            val name = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.NAME))
            val repoCount = getInt(getColumnIndexOrThrow(ProviderContract.BioEntity.REPOCOUNT))
            val followerUrl = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.FOLLOWERSURL))
            val followingUrl = getString(getColumnIndexOrThrow(ProviderContract.BioEntity.FOLLOWINGURL))
            listGeneralBio.add(
                GeneralBio(
                    username = username,
                    avatar = avatar,
                    company = company,
                    followerCount = followerCount,
                    followingCount = followingCount,
                    location = location,
                    name = name,
                    repoCount = repoCount,
                    followersUrl = followerUrl,
                    followingUrl = followingUrl
                )
            )
        }
    }
    return listGeneralBio
}