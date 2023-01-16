package com.myrickmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.myrickmorty.application.Constants.PAGE_INDEX
import com.myrickmorty.core.Resource
import com.myrickmorty.domain.RepositoryCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RepositoryCharacter) : ViewModel() {

    val listData = repository.listDataRepositoryImpl().cachedIn(viewModelScope)

    fun fetchCharacters() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.getAllCharacters((PAGE_INDEX))))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}