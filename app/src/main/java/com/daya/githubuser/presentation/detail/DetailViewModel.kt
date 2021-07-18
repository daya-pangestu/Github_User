package com.daya.githubuser.presentation.detail

import androidx.lifecycle.*
import com.daya.githubuser.data.Resource
import com.daya.githubuser.domain.model.GeneralBio
import com.daya.githubuser.domain.usecase.detail.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
private val getDetailBioUseCase: GetDetailBioUseCase,
private val getListFollowersUseCase: GetListFollowersUseCase,
private val getListFollowingUseCase: GetListFollowingUseCase,
private val addFavoriteUseCase: AddFavoriteUseCase,
private val removeUserFavoriteUseCase: RemoveUserFavoriteUseCase,
private val isUserFavoriteUseCase: IsUserFavoriteUseCase,
private val getCurrentDetailFavoriteUserUseCase: GetCurrentDetailFavoriteUserUseCase
) : ViewModel() {

    private val _bioLiveData = MutableLiveData<GeneralBio>()

    fun sculptingBio(bio: GeneralBio?) {
            _bioLiveData.value = bio!! //guaranteed to be NonNull
    }

    @Suppress("UNCHECKED_CAST")
    val getDetailBioLiveData = _bioLiveData.switchMap { bio ->
        liveData {
            emit(Resource.Loading)
            when {
                bio.isPersisted() -> {
                    val generalBio = getCurrentDetailFavoriteUserUseCase(bio.username)
                    emit(generalBio)
                }
                bio.needFetch() -> {
                    val netBio = getDetailBioUseCase(bio.username)
                    emit(netBio)
                }
                else -> {
                    val listFollowers  = this@DetailViewModel.getListFollowersUseCase(bio.username)
                    val listFollowing = this@DetailViewModel.getListFollowingUseCase(bio.username)

                    var errorMessage : String? = ""
                    val actualListFollowers = when (listFollowers) {
                        is Resource.Success -> {
                            listFollowers.data
                        }
                        else -> {
                            errorMessage = (listFollowers as Resource.Error).exceptionMessage
                            null
                        }
                    }

                    val actualListFollowing = when (listFollowing) {
                        is Resource.Success -> {
                            listFollowing.data
                        }
                        else -> {
                            errorMessage = (listFollowing as Resource.Error).exceptionMessage
                            null
                        }
                    }

                    if (actualListFollowers == null || actualListFollowing == null) {
                        emit(Resource.Error(errorMessage))
                        return@liveData
                    }
                    bio.followers = actualListFollowers
                    bio.followings = actualListFollowing

                    val resBio = bio.let { Resource.Success(it) }
                    emit(resBio)
                }
            }
        }
    }

    private fun GeneralBio?.needFetch(): Boolean {
        return if (this != null) {
            name.isEmpty()
                    && company.isEmpty()
                    && location.isEmpty()
                    && followingCount == 0
                    && followerCount == 0
                    && repoCount == 0
                    && followers.isEmpty()
                    && followings.isEmpty()
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
        val currentBio = _bioLiveData.value ?: return@launch
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