package com.myrickmorty.core

import com.myrickmorty.model.data.ResponseApi
import retrofit2.Response

sealed class State<T>()  {

    class Success<T>(val results: Response<ResponseApi>) : State<T>()
    class Failure<T>(val cause: Throwable) : State<T>()
    class Loading<T>() : State<T>()
}