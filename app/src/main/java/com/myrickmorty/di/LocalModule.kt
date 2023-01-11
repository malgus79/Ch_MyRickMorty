package com.myrickmorty.di

import android.content.Context
import androidx.room.Room
import com.myrickmorty.core.Constants.APP_DATABASE_NAME
import com.myrickmorty.model.local.AppDatabase
import com.myrickmorty.model.local.RickMortyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providesRoom (@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesRickMortyDao(rickmortydb: AppDatabase): RickMortyDao {
        return rickmortydb.characterDao()
    }
}