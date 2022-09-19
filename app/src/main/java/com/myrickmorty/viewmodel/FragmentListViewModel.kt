package com.myrickmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.myrickmorty.model.api.ApiService
import com.myrickmorty.repository.paging.RickyMortyPagingSource
import com.myrickmorty.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickyMortyPagingSource(repository)

    }.flow.cachedIn(viewModelScope)

}

class MovieViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApiService::class.java).newInstance(apiService)
    }
}
