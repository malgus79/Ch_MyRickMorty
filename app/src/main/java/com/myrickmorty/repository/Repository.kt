package com.myrickmorty.repository

import com.myrickmorty.model.api.ApiService
import com.myrickmorty.model.data.ResponseApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val ApiService: ApiService) {

    suspend fun getAllCharacters(currentPage: Int): Response<ResponseApi> {
    return ApiService.getAllCharacters(currentPage)
    }
}