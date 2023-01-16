package com.myrickmorty.model.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.*
import com.myrickmorty.data.local.AppDatabase
import com.myrickmorty.data.local.FavoriteEntity
import com.myrickmorty.data.local.RickMortyDao
import com.myrickmorty.data.local.RickMortyEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RickMortyDaoTest  {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: RickMortyDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()
        dao = database.characterDao()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testSaveFavoriteCharacter() = runTest {

        val favoriteEntity = FavoriteEntity(
            1,
            "2017-11-04T18:48:46.250Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/1"
        )
        dao.saveFavoriteCharacter(favoriteEntity)

        val character = dao.getAllFavoriteCharactersWithChanges()

        assertThat(character).isNotEmpty()
        assertThat(character).contains(favoriteEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetAllFavoriteCharacter() = runTest {

        val favoriteEntity = FavoriteEntity(
            1,
            "2017-11-04T18:48:46.250Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/1"
        )
        val favoriteEntity2 = FavoriteEntity(
            2,
            "2017-11-04T18:50:21.651Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            "Morty Smith",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/2"
        )
        dao.saveFavoriteCharacter(favoriteEntity)
        dao.saveFavoriteCharacter(favoriteEntity2)

        val character = dao.getAllFavoriteCharacters()

        assertThat(character).isNotEmpty()
        assertThat(character.size).isEqualTo(2)
        assertThat(character).contains(favoriteEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterById() = runTest {

        val favoriteEntity = FavoriteEntity(
            1,
            "2017-11-04T18:48:46.250Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/1"
        )
        dao.saveFavoriteCharacter(favoriteEntity)

        val character = dao.getCharacterById(favoriteEntity.id)

        assertThat(character).isNotNull()
        assertThat(character).isEqualTo(favoriteEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDeleteFavoriteCharacter() = runTest {

        val favoriteEntity = FavoriteEntity(
            1,
            "2017-11-04T18:48:46.250Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/1"
        )
        dao.saveFavoriteCharacter(favoriteEntity)
        dao.deleteFavoriteCharacter(favoriteEntity)

        val character = dao.getAllFavoriteCharactersWithChanges()

        assertThat(character).isEmpty()
        assertThat(character).doesNotContain(favoriteEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSaveCharacter() = runTest {
        val characterEntity = RickMortyEntity(
            1,
            "2017-11-04T18:48:46.250Z",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez",
            "Human",
            "Alive",
            "",
            "https://rickandmortyapi.com/api/character/1"
        )
        dao.saveCharacter(characterEntity)

        val character = dao.getCharacters("")

        assertThat(character).isNotEmpty()
    }

    @After
    fun tearDown() {
        database.close()
    }
}