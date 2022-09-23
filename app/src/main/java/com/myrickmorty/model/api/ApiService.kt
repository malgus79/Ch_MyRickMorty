package com.myrickmorty.model.api

import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //This endpoint returns a list of "page"
    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        // @Query("count") size:Int,
        @Query("page") page: Int

    ): Response<ResponseApi>
}