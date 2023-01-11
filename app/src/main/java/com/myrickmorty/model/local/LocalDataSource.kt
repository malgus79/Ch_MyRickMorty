package com.myrickmorty.model.local

import com.myrickmorty.model.data.RickMortyList
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val rickMortyDao: RickMortyDao) {

    suspend fun getAllCharacters(): RickMortyList {
        return rickMortyDao.getAllCharacters().toRickMortyList()
    }

    suspend fun saveCharacter(character: RickMortyEntity) {
        rickMortyDao.saveCharacter(character)
    }

}