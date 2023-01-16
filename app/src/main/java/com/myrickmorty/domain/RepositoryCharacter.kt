package com.myrickmorty.domain

import androidx.paging.PagingData
import com.myrickmorty.core.Resource
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.data.model.RickMortyList
import kotlinx.coroutines.flow.Flow

interface RepositoryCharacter {

    fun listDataRepositoryImpl(): Flow<PagingData<RickMorty>>
    suspend fun getAllCharacters(currentPage: Int): RickMortyList
    fun getFavoriteCharacters(): RickMortyList
    suspend fun isCharacterFavorite(character: RickMorty): Boolean
    suspend fun deleteFavoriteCharacter(character: RickMorty)
    suspend fun saveFavoriteCharacter(character: RickMorty)
    fun getCharacterByName(characterSearched: String?): Flow<Resource<List<RickMorty>>>

}