package com.daya.core.data.di.bio

import androidx.room.Embedded
import androidx.room.Relation

data class BioEntityWithFollowersFollowing(
    @Embedded val bio : BioEntity,

    @Relation(
        parentColumn = "bio_user_name",
        entityColumn = "userName"
    )
    val followers : List<FollowersEntity>,

    @Relation(
        parentColumn = "bio_user_name",
        entityColumn = "userName"
    )
    val following : List<FollowingEntity>

)