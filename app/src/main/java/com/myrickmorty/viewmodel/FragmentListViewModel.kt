package com.myrickmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.repository.Repository
import com.myrickmorty.repository.paging.RickyMortyPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    val getAllCharacters: Flow<PagingData<RickMorty>> =
        Pager(config = PagingConfig(20, enablePlaceholders = false)) {
            RickyMortyPagingSource(repository)
        }.flow.cachedIn(viewModelScope)

    fun getCharacter() = Pager(
        config = PagingConfig(20, enablePlaceholders = false)
    ) {
        RickyMortyPagingSource(repository)
    }

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickyMortyPagingSource(repository)

    }.flow.cachedIn(viewModelScope)

}