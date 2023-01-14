package com.myrickmorty.model.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseNotOpen() {
        assertThat(database.isOpen).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseOpen() = runTest {
        executeDatabaseFunction()
        assertThat(database.isOpen).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabaseVersionIsCurrent() = runTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.version).isEqualTo(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabasePathIsMemory() = runTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.path).isEqualTo(":memory:")
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    private fun executeDatabaseFunction() = runTest {
        val rickMortyEntity = RickMortyEntity(
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
        database.characterDao().saveCharacter(rickMortyEntity)
    }

}