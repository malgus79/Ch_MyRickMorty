package com.myrickmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.myrickmorty.core.Constants.PAGE_INDEX
import com.myrickmorty.core.Resource
import com.myrickmorty.domain.Repository
import com.myrickmorty.model.paging.DataPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun fetchCharacters() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.getAllCharacters((PAGE_INDEX))))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    val listData = Pager(PagingConfig(pageSize = 1)) {
        DataPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}