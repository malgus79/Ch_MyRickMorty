package com.myrickmorty.data.local

import com.myrickmorty.core.Resource
import com.myrickmorty.data.model.RickMorty
import com.myrickmorty.data.model.RickMortyList
import com.myrickmorty.data.model.asFavoriteEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val rickMortyDao: RickMortyDao) {

    suspend fun getAllCharacters(): RickMortyList {
        return rickMortyDao.getAllCharacters().toRickMortyList()
    }

    suspend fun saveCharacter(character: RickMortyEntity) {
        rickMortyDao.saveCharacter(character)
    }

    fun getFavoriteCharacters(): RickMortyList {
        return rickMortyDao.getAllFavoriteCharactersWithChanges().toRickMortyList()
    }

    suspend fun isCharacterFavorite(character: RickMorty): Boolean {
        return rickMortyDao.getCharacterById(character.id) != null
    }

    suspend fun deleteCharacter(character: RickMorty) {
        return rickMortyDao.deleteFavoriteCharacter(character.asFavoriteEntity())
    }

    suspend fun saveFavoriteCharacter(character: RickMorty) {
        return rickMortyDao.saveFavoriteCharacter(character.asFavoriteEntity())
    }

    suspend fun getCachedCharacters(characterSearched: String?): Resource<List<RickMorty>> {
        return Resource.Success(rickMortyDao.getCharacters(characterSearched).toRickMortyList())
    }

    suspend fun deleteCached() {
        return rickMortyDao.deleteCached()
    }
}



