package com.myrickmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myrickmorty.core.Resource
import com.myrickmorty.core.connectivity.CheckInternet
import com.myrickmorty.data.local.LocalDataSource
import com.myrickmorty.data.local.RickMortyEntity
import com.myrickmorty.data.local.toRickMortyEntity
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.data.model.RickMortyList
import com.myrickmorty.data.paging.DataPagingSource
import com.myrickmorty.data.remote.RemoteDataSource
import com.myrickmorty.domain.RepositoryCharacter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RepositoryCharacter {

    override fun listDataRepositoryImpl(): Flow<PagingData<RickMorty>> = Pager(
        config = PagingConfig(1),
    ) {
        DataPagingSource(repositoryImpl = RepositoryImpl(remoteDataSource, localDataSource))
    }.flow

    override suspend fun getAllCharacters(currentPage: Int): RickMortyList {
        return if (CheckInternet.isNetworkAvailable()) {
            localDataSource.deleteCached()

            remoteDataSource.getAllCharacters(currentPage).body()?.results?.forEach {
                localDataSource.saveCharacter(it.toRickMortyEntity())
            }
            localDataSource.getAllCharacters()
        } else {
            localDataSource.getAllCharacters()
        }
    }

    override fun getFavoriteCharacters(): RickMortyList {
        return localDataSource.getFavoriteCharacters()
    }

    override suspend fun isCharacterFavorite(character: RickMorty): Boolean {
        return localDataSource.isCharacterFavorite(character)
    }

    override suspend fun deleteFavoriteCharacter(character: RickMorty) {
        localDataSource.deleteCharacter(character)
    }

    override suspend fun saveFavoriteCharacter(character: RickMorty) {
        localDataSource.saveFavoriteCharacter(character)
    }

    override fun getCharacterByName(characterSearched: String?): Flow<Resource<List<RickMorty>>> =
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