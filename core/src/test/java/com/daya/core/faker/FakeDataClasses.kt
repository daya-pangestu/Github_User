package com.daya.core.faker

import com.daya.core.data.profile.network.NetWorkBio
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

    val generalBio
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
    
}