package com.myrickmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.domain.Repository
import io.mockk.MockKStubScope
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class FragmentListViewModelTest {

    @get:Rule
    var testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when characterLis retrieves data null or Empty`() = runTest {

        //Arrange
        val character = RickMorty("2017-11-04T20:03:34.737Z",
            listOf("https://rickandmortyapi.com/api/episode/28"),
            "Male",
            8,
            "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            "Adjudicator Rick",
            "Human",
            "Dead",
            "null",
            "")
        val characterList = ResponseApi(listOf())

        val repository = mockk<Repository>()

        coEvery { repository.getAllCharacters(1) } returns characterList

        val viewModel = FragmentListViewModel(repository)

        //Act
        viewModel.getCharacters()

        //Assert
        assert(viewModel.characterList.value?.equals(null) == !characterList.results.isNullOrEmpty())

    }

    @Test
    fun `when characterLis retrieves data correctly`() = runTest {

        //Arrange
        val character = RickMorty("2017-11-04T20:03:34.737Z",
            listOf("https://rickandmortyapi.com/api/episode/28"),
            "Male",
            8,
            "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            "Adjudicator Rick",
            "Human",
            "Dead",
            "null",
            "")
        val characterList = ResponseApi(listOf())

        val repository = mockk<Repository>()

        coEvery { repository.getAllCharacters(1) } returns characterList

        val viewModel = FragmentListViewModel(repository)

        //Act
        viewModel.getCharacters()

        //Assert
        assert(viewModel.characterList.value!!.equals(character) == characterList.results?.isNotEmpty())
    }

    @Test
    fun `when characterLis fail to retrieve data correctly`() = runTest {

        //Arrange
        val characterList = ResponseApi(listOf())

        val repository = mockk<Repository>()

        coEvery { repository.getAllCharacters(1) } returns characterList

        val viewModel = FragmentListViewModel(repository)

        //Act
        viewModel.getCharacters()

        //Assert
        assert(viewModel.characterList.value?.equals(characterList) == !characterList.results.isNullOrEmpty())
    }
}

private infix fun <T, B> MockKStubScope<T, B>.returns(characterList: ResponseApi) {

}
