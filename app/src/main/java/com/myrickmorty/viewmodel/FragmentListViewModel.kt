package com.myrickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.myrickmorty.core.ResourceNotFoundException
import com.myrickmorty.core.State
import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.paging.DataPagingSource
import com.myrickmorty.repository.Repository
import com.myrickmorty.utils.Constants.PAGE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    //Internal/External MutableLiveData
    private val _characterList = MutableLiveData<State<Response<ResponseApi>>>()
    val characterList: LiveData<State<Response<ResponseApi>>> = _characterList

    //Downloads data from api with repository
    fun getCharacters() {
        _characterList.postValue(State.Loading())
        viewModelScope.launch {
            try {
                val characterList = repository.getAllCharacters(PAGE_INDEX)
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

    //Load data from DataPagingSource
    val listData = Pager(PagingConfig(pageSize = 1)) {
        DataPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}

/*
    val getAllCharacters: Flow<PagingData<RickMorty>> =
        Pager(config = PagingConfig(20, enablePlaceholders = false)) {
            RickyMortyPagingSource(repository)
        }.flow.cachedIn(viewModelScope)

    fun getCharacter() = Pager(
        config = PagingConfig(20, enablePlaceholders = false)
    ) {
        RickyMortyPagingSource(repository)
    }
*/

