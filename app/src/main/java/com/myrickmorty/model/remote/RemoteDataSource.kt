package com.myrickmorty.model.remote

import com.myrickmorty.model.data.RickMortyList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllCharacters(currentPage: Int): Response<RickMortyList> {
        return apiService.getAllCharacters(currentPage)
    }
}