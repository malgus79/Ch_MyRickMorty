package com.myrickmorty.data.remote

import com.myrickmorty.core.Resource
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.data.model.RickMortyList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllCharacters(currentPage: Int): Response<RickMortyList> {
        return apiService.getAllCharacters(currentPage)
    }

    fun getCharacterByName(characterSearched: String): Flow<Resource<List<RickMorty>>> =
        callbackFlow {
            trySend(
                Resource.Success(
                    (apiService.getCharacterByName(characterSearched))
                )
            )
            awaitClose { close() }
        }
}