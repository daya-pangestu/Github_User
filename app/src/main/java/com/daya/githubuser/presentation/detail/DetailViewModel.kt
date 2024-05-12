package com.daya.githubuser.presentation.detail

import androidx.lifecycle.*
import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.domain.usecase.detail.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
private val getDetailBioUseCase: GetDetailBioUseCase,
private val addFavoriteUseCase: AddFavoriteUseCase,
private val removeUserFavoriteUseCase: RemoveUserFavoriteUseCase,
private val isUserFavoriteUseCase: IsUserFavoriteUseCase,
private val getCurrentDetailFavoriteUserUseCase: GetCurrentDetailFavoriteUserUseCase
) : ViewModel() {

    private val _bioLiveData = MutableLiveData<GeneralBio>()

    fun setBio(bio: GeneralBio?) {
            _bioLiveData.value = bio!! //guaranteed to be NonNull
    }

    @Suppress("UNCHECKED_CAST")
    val getDetailBioLiveData = _bioLiveData.switchMap { bio ->
        liveData {
            emit(Resource.Loading)
            when {
                bio.isPersisted() -> {
                    val generalBio = getCurrentDetailFavoriteUserUseCase(bio.username)
                    isUserFavorite.value = true
                    emit(generalBio)
                }
                bio.needFetch() -> {
                    val netBio = getDetailBioUseCase(bio.username)
                    emit(netBio)
                }
                else -> emit(Resource.Success(bio))
            }
        }
    }

    private fun GeneralBio?.needFetch(): Boolean {
        return if (this != null) {
            name.isEmpty()
                    && company.isEmpty()
                    && location.isEmpty()
                    && repoCount == 0
        } else true
    }

    private suspend fun GeneralBio?.isPersisted(): Boolean {
        return when (val isExistRes = isUserFavoriteUseCase(this!!.username)) {
            is Resource.Success -> {
                val isExist = isExistRes.data
                isExist
            }
            else -> false
        }
    }

    fun addUserToFavorite() = viewModelScope.launch {
        val currentBio = try {
            (getDetailBioLiveData.value as Resource.Success).data
        } catch (e: ClassCastException) {
            return@launch
        }
        if (currentBio.needFetch()) return@launch

        val rowId = withContext(Dispatchers.Default) {
            addFavoriteUseCase(currentBio)
        }
        val isExist = when (rowId) {
            is Resource.Success -> rowId.data > 0L
            else -> false
        }
        isUserFavorite.value = isExist
    }

    fun removeUserFromFavorite() = viewModelScope.launch {
        _bioLiveData.value?.username?.let { // TODO provide logic for non-ready profile
            val removed = launch {  removeUserFavoriteUseCase(it) }
            removed.join()
            isUserFavorite.value = false
        }
    }

    private val isUserFavorite = MutableLiveData(false)

    fun isUserFavLiveData(): MutableLiveData<Boolean> {
        return isUserFavorite
    }
}