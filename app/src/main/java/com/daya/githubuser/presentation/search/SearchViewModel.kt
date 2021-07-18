package com.daya.githubuser.presentation.search

import androidx.lifecycle.*
import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.domain.usecase.profile.SearchUserUseCase
import com.daya.core.utils.debounce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _searchLiveData = MutableLiveData<String>()

    fun submitQuery(name: String) = viewModelScope.launch {
        _searchLiveData.value = name.trim()
    }

    fun observerSearchResult(): LiveData<Resource<List<GeneralBio>>> {
        return _searchLiveData
            .distinctUntilChanged()
            .debounce()
            .switchMap { query ->
            liveData(context = viewModelScope.coroutineContext) {
                if (query.isEmpty()) return@liveData
                val netBio = if (latestValue == null) {
                    emit(Resource.Loading)
                    searchUserUseCase(query)
                } else {
                    latestValue!! //guaranteed non null
                }
                emit(netBio)
            }
        }
    }
}