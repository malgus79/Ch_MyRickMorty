package com.myrickmorty.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RickMortyEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): RickMortyDao
}