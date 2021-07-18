package com.daya.githubuser.domain.usecase.profile

import com.daya.githubuser.domain.model.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
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