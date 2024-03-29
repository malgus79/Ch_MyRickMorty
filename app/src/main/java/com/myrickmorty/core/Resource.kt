package com.myrickmorty.core

import com.myrickmorty.data.model.RickMortyList

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    class Success<out T>(val data: RickMortyList) : Resource<T>()
    class Failure(val exception: Exception) : Resource<Nothing>()
}