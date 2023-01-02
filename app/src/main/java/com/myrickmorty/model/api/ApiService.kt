package com.myrickmorty.model.api

import com.myrickmorty.core.Constants
import com.myrickmorty.model.data.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        @Query("page") page: Int

    ): Response<ResponseApi>
}