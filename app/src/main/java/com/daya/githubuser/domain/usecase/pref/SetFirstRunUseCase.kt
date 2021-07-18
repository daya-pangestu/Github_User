package com.daya.githubuser.domain.usecase.pref

import com.daya.githubuser.data.pref.SharedPreferenceStorage
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetFirstRunUseCase @Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val preferenceStorage: SharedPreferenceStorage

) : UseCase<Boolean, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameters: Boolean) {
        preferenceStorage.isFirstRun = parameters
    }
}