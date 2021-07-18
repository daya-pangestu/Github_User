package com.daya.githubuser.domain.profile

import com.daya.githubuser.data.Resource
import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.FlowUseCase
import com.daya.githubuser.xtension.toFollowersFollowing
import com.daya.githubuser.xtension.toGeneralBio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListFavoriteUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository
) : FlowUseCase<Unit, List<GeneralBio>>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<Resource<List<GeneralBio>>> {
        val listFavEntityWithFollowerFollowing = repository.getAllFavoriteEntity()
        val listGeneralBio = listFavEntityWithFollowerFollowing.map { listBioEntity ->
            val bio = listBioEntity.map {
                it.toGeneralBio().apply {
                    followers = it.followers.toFollowersFollowing()
                    followings = it.following.toFollowersFollowing()
                }
            }
            bio
        }
        return listGeneralBio.map {
            Resource.Success(it)
        }
    }
}