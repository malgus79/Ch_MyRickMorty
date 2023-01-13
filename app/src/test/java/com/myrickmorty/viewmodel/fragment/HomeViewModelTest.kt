package com.myrickmorty.viewmodel.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myrickmorty.accessdata.JSONFileLoader
import com.myrickmorty.core.common.Constants
import com.myrickmorty.core.common.Constants.PAGE_INDEX
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.model.remote.ApiService
import com.myrickmorty.viewmodel.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModelTest {

    @get:Rule
    val instantExecutionerRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainDispatcherRule()

    private lateinit var apiService: ApiService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup() {
        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun `check fetch characters is not null test`() {
        runBlocking {
            val result = apiService.getAllCharacters(PAGE_INDEX)
            assertThat(result.body()?.results, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item characters for page test`() {
        runBlocking {
            val result = apiService.getAllCharacters(PAGE_INDEX)
            assertThat(result.body()?.results?.size, `is`(20))
        }
    }

    @Test
    fun `check first item character (id = 1) test`() {
        runBlocking {
            val result = apiService.getAllCharacters(PAGE_INDEX)
            assertThat(
                result.body()?.results?.first(),
                `is`(
                    RickMorty(
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
                )
            )
        }
    }

    @Test
    fun `check characters remote with local test`() {
        runBlocking {
            val remoteResult = apiService.getAllCharacters(PAGE_INDEX)
            val localResult = JSONFileLoader().loadCharacterList("character_response_success")

            assertThat(
                localResult?.results?.size,
                `is`(remoteResult.body()?.results?.size)
            )

            assertThat(
                localResult?.results.isNullOrEmpty(),
                `is`(remoteResult.body()?.results?.isEmpty())
            )

            assertThat(
                localResult?.results?.contains(RickMorty()),
                `is`(remoteResult.body()?.results?.contains(RickMorty()))
            )

            assertThat(
                localResult?.results?.indices,
                `is`(remoteResult.body()?.results?.indices)
            )
        }
    }
}