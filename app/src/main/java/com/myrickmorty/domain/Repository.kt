package com.myrickmorty.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myrickmorty.core.Resource
import com.myrickmorty.core.connectivity.CheckInternet
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.model.data.RickMortyList
import com.myrickmorty.model.local.LocalDataSource
import com.myrickmorty.model.local.RickMortyEntity
import com.myrickmorty.model.local.toRickMortyEntity
import com.myrickmorty.model.paging.DataPagingSource
import com.myrickmorty.model.remote.RemoteDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    val listDataRepository = Pager(
        config = PagingConfig(1),
    ) {
        DataPagingSource(repository = Repository(remoteDataSource, localDataSource))
    }.flow

    suspend fun getAllCharacters(currentPage: Int): RickMortyList {
        return if (CheckInternet.isNetworkAvailable()) {
            remoteDataSource.getAllCharacters(currentPage).body()?.results?.forEach {
                localDataSource.saveCharacter(it.toRickMortyEntity())
            }
            localDataSource.getAllCharacters()
        } else {
            localDataSource.getAllCharacters()
        }
    }

    fun getFavoriteCharacters(): RickMortyList {
        return localDataSource.getFavoriteCharacters()
    }

    suspend fun isCharacterFavorite(character: RickMorty): Boolean {
        return localDataSource.isCharacterFavorite(character)
    }

    suspend fun deleteFavoriteCharacter(character: RickMorty) {
        localDataSource.deleteCharacter(character)
    }

    suspend fun saveFavoriteCharacter(character: RickMorty) {
        localDataSource.saveFavoriteCharacter(character)
    }

    fun getCharacterByName(characterSearched: String?): Flow<Resource<List<RickMorty>>> =
        callbackFlow {
            trySend(getCachedCharacters(characterSearched))

            remoteDataSource.getCharacterByName(characterSearched.toString()).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        for (character in it.data.results) {
                            saveCharacter(character.toRickMortyEntity())
                        }
                        trySend(getCachedCharacters(characterSearched))
                    }
                    is Resource.Failure -> {
                        trySend(getCachedCharacters(characterSearched))
                    }
                    else -> {}
                }
            }
            awaitClose { cancel() }
        }

    private suspend fun getCachedCharacters(characterSearched: String?): Resource<List<RickMorty>> {
        return localDataSource.getCachedCharacters(characterSearched)
    }

    private suspend fun saveCharacter(character: RickMortyEntity) {
        localDataSource.saveCharacter(character)
    }
}