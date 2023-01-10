package com.myrickmorty.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myrickmorty.model.remote.ApiService
import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.model.paging.DataPagingSource
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    val listDataRepository = Pager(
        config = PagingConfig(1),
    ) {
        DataPagingSource(repository = Repository(apiService))
    }.flow

    suspend fun getAllCharacters(currentPage: Int): Response<ResponseApi> {
        return apiService.getAllCharacters(currentPage)
    }
}