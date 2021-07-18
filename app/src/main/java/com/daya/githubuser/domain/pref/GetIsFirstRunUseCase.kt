package com.daya.githubuser.domain.pref

import com.daya.githubuser.data.pref.SharedPreferenceStorage
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetIsFirstRunUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val preferenceStorage: SharedPreferenceStorage
) : UseCase<Unit, Boolean>(coroutineDispatcher) {
    override suspend fun execute(parameters: Unit) = preferenceStorage.isFirstRun
}