package com.myrickmorty.model.local

import androidx.room.*

@Dao
interface RickMortyDao {

    @Query("SELECT * FROM character_entity")
    suspend fun getAllCharacters(): List<RickMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter(character: RickMortyEntity)

    @Query("SELECT * FROM favorite_entity")
    fun getAllFavoriteCharactersWithChanges(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_entity WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int?): FavoriteEntity?

    @Delete
    suspend fun deleteFavoriteCharacter(favorites: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteCharacter(character: FavoriteEntity)

}