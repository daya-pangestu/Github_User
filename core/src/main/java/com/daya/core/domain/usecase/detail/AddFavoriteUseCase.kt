package com.daya.core.domain.usecase.detail

import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
import com.daya.core.utils.toBioEntity
import com.daya.core.utils.toFollowersEntity
import com.daya.core.utils.toFollowingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFavoriteUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repo : IProfileRepository
) : UseCase<GeneralBio?, Long>(coroutineDispatcher) {

    override suspend fun execute(parameters: GeneralBio?): Long {
        val nonNullBio = parameters!!
        val bioEntity =  nonNullBio.toBioEntity()

        val followers = nonNullBio.toFollowersEntity()
        val following = nonNullBio.toFollowingEntity()

        return coroutineScope {
            val rowId = async {
                 repo.addUserFavorite(bioEntity)
            }
            launch {
                repo.insertfollowers(followers)
                repo.insertFollowing(following)
            }.join()

            rowId.await()
        }

    }

}