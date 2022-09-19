package com.myrickmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.myrickmorty.model.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel
@Inject
constructor(
    private val apiService: ApiService
) : ViewModel() {

//    val listData = Pager(PagingConfig(pageSize = 1)) {
//        RickyMortyPagingSource(apiService)
//
//    }.flow.cachedIn(viewModelScope)

}
