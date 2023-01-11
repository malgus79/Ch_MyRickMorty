package com.myrickmorty.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RickMortyDao {

    @Query("SELECT * FROM character_entity")
    suspend fun getAllCharacters(): List<RickMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter( character: RickMortyEntity)
}