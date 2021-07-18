package com.daya.githubuser.domain.profile

import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetListBioUseCase
@Inject
constructor(
        private val repo: ProfileRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<GeneralBio>>(dispatcher) {

    override suspend fun execute(parameters: Unit): List<GeneralBio> {
        return repo.getListBioFromJson()
    }
}