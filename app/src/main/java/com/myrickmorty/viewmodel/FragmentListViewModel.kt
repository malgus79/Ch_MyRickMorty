package com.myrickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myrickmorty.core.ResourceNotFoundException
import com.myrickmorty.core.State
import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.repository.Repository
import com.myrickmorty.repository.paging.RickyMortyPagingSource
import com.myrickmorty.utils.Constants
import com.myrickmorty.utils.Constants.DEFAULT_PAGE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _characterList = MutableLiveData<State<ResponseApi>>()
    val characterList: LiveData<State<ResponseApi>> = _characterList

    fun getCharacters() {
        _characterList.postValue(State.Loading())
        viewModelScope.launch {
            try {
                val characterList = repository.getAllCharacters(DEFAULT_PAGE_INDEX)
                if (characterList.body()?.results.isNullOrEmpty()) {
                    _characterList.postValue(State.Failure(ResourceNotFoundException()))
                } else {
                    _characterList.postValue(State.Success(characterList))
                }
            } catch (e: Exception) {
                _characterList.postValue(State.Failure(e))
            }
        }
    }

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickyMortyPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}

//    val getAllCharacters: Flow<PagingData<RickMorty>> =
//        Pager(config = PagingConfig(20, enablePlaceholders = false)) {
//            RickyMortyPagingSource(repository)
//        }.flow.cachedIn(viewModelScope)
//
//    fun getCharacter() = Pager(
//        config = PagingConfig(20, enablePlaceholders = false)
//    ) {
//        RickyMortyPagingSource(repository)
//    }


