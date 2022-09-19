package com.myrickmorty.core

sealed class State<out T> {
    class Loading<out T> : State<T>()
    data class Success<out T>(val data: T) : State<T>()
    data class Failure(val exception: Exception) : State<Nothing>()
}