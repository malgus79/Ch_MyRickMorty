package com.myrickmorty.core

import com.myrickmorty.model.data.ResponseRickMorty
import retrofit2.Response

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    class Success<out T>(val data: Response<ResponseRickMorty>) : Resource<T>()
    class Failure(val exception: Exception) : Resource<Nothing>()
}