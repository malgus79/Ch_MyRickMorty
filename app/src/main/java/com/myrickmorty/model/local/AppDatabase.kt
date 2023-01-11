package com.myrickmorty.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [RickMortyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): RickMortyDao
}