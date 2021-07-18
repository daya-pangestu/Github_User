package com.daya.core.domain.usecase.pref

import com.daya.core.data.pref.SharedPreferenceStorage
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.usecase.UseCase
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