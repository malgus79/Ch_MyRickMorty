package com.myrickmorty.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.model.data.RickMortyList
import com.myrickmorty.model.local.LocalDataSource
import com.myrickmorty.model.local.toRickMortyEntity
import com.myrickmorty.model.paging.DataPagingSource
import com.myrickmorty.model.remote.RemoteDataSource
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
        remoteDataSource.getAllCharacters(currentPage).body()?.results?.forEach {
            localDataSource.saveCharacter(it.toRickMortyEntity())
        }
        return localDataSource.getAllCharacters()
    }

    suspend fun getFavoriteCharacters(): RickMortyList {
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
}