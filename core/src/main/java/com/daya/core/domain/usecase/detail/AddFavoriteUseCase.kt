package com.daya.core.domain.usecase.detail

import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.model.GeneralBio
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
import com.daya.core.utils.toBioEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

        return coroutineScope {
            val rowId = async {
                 repo.addUserFavorite(bioEntity)
            }
            rowId.await()
        }

    }

}