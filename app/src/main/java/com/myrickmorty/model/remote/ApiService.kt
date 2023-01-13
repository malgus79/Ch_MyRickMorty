package com.myrickmorty.model.remote

import com.myrickmorty.core.common.Constants.PAGE
import com.myrickmorty.core.common.Constants.PATH_URL
import com.myrickmorty.model.data.RickMortyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(PATH_URL)
    suspend fun getAllCharacters(
        @Query(PAGE) page: Int

    ): Response<RickMortyList>

    @GET(PATH_URL)
    suspend fun getCharacterByName(
        @Query(value = "") characterSearched: String
    ): RickMortyList
}