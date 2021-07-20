package com.daya.core.faker

import com.daya.core.data.di.bio.BioEntity
import com.daya.core.data.di.bio.BioEntityWithFollowersFollowing
import com.daya.core.data.profile.network.NetWorkBio
import com.daya.core.data.profile.network.NetWorkUsers
import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.domain.model.GeneralBio
import com.daya.core.faker.KotlinFaker.faker
import com.daya.core.faker.KotlinFaker.randomNumber
import com.daya.core.faker.KotlinFaker.randomUrl

object FakeDataClasses {
    val networkBio
    get() = List(5){
        NetWorkBio(
            login  = faker.internet.email(),
            avatar_url  = randomUrl,
            followers_url  = "",
            following_url  = "",
            name  = faker.name.firstName(),
            company  = faker.company.name(),
            location  = faker.address.cityName(),
            followers  = randomNumber,
            following  = randomNumber,
            public_repos  = randomNumber,
        )
    }

    val listGeneralBio
        get() = List(10){
            GeneralBio(
                avatar = randomUrl,
                company = faker.company.name(),
                followerCount = randomNumber,
                followingCount = randomNumber,
                location = faker.address.cityName(),
                name = faker.name.firstName(),
                repoCount = randomNumber,
                username = faker.name.neutralFirstName(), // or login
                followersUrl = "",
                followingUrl = "",
                followers = emptyList(),
                followings = emptyList(),
            )
        }

    val entityBio
        get() = List(5){
            BioEntity(
                username  = faker.app.author(),
                avatar  = randomUrl,
                followersUrl  = "",
                followingUrl= "",
                name  = faker.name.firstName(),
                company  = faker.company.name(),
                location  = faker.address.cityName(),
                followerCount  = randomNumber,
                followingCount  = randomNumber,
                repoCount  = randomNumber,
            )
        }


    val bioEntityWithFollowersFollowing
        get() = BioEntityWithFollowersFollowing(
            bio = BioEntity(
                username = faker.app.author(),
                avatar = randomUrl,
                followersUrl = "",
                followingUrl = "",
                name = faker.name.firstName(),
                company = faker.company.name(),
                location = faker.address.cityName(),
                followerCount = randomNumber,
                followingCount = randomNumber,
                repoCount = randomNumber,
            ),
            followers = emptyList(),
            following = emptyList(),

            )

    val networkUsers
        get() = NetWorkUsers(
            listNetWorkBio = networkBio,
            incomplete_results = false,
            total_count = 5
        )
    
}