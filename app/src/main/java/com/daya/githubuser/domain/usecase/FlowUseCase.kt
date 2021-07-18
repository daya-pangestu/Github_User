package com.daya.githubuser.domain.usecase

import com.daya.githubuser.data.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Resource<R>> = execute(parameters)
        .onStart { emit(Resource.Loading) }
        .catch { e -> emit(Resource.Error(e.localizedMessage)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Resource<R>>
}
