package com.daya.core.domain.usecase.profile

import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetListBioUseCase
@Inject
constructor(
        private val repo: IProfileRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<GeneralBio>>(dispatcher) {

    override suspend fun execute(parameters: Unit): List<GeneralBio> {
        return repo.getListBioFromJson()
    }
}