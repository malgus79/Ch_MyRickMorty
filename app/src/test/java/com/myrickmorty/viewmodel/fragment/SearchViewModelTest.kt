package com.myrickmorty.viewmodel.fragment

import com.myrickmorty.accessdata.JSONFileLoader
import com.myrickmorty.core.common.Constants.BASE_URL
import com.myrickmorty.core.common.Constants.PAGE_INDEX
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.model.remote.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModelTest {

    private lateinit var apiService: ApiService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup() {
        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun `check fetch character searched is not null test`() {
        runBlocking {
            val result = apiService.getCharacterByName("")
            MatcherAssert.assertThat(result.results, Matchers.`is`(Matchers.notNullValue()))
        }
    }

    @Test
    fun `check item character searched for page test`() {
        runBlocking {
            val result = apiService.getCharacterByName("")
            MatcherAssert.assertThat(result.results.size, Matchers.`is`(20))
        }
    }

    @Test
    fun `check character searched remote with local test`() {
        runBlocking {
            val remoteResult = apiService.getCharacterByName("")
            val localResult = JSONFileLoader().loadCharacterList("character_response_success")

            MatcherAssert.assertThat(
                localResult?.results?.size,
                Matchers.`is`(remoteResult.results.size)
            )

            MatcherAssert.assertThat(
                localResult?.results.isNullOrEmpty(),
                Matchers.`is`(remoteResult.results.isEmpty())
            )

            MatcherAssert.assertThat(
                localResult?.results?.contains(RickMorty()),
                Matchers.`is`(remoteResult.results.contains(RickMorty()))
            )

            MatcherAssert.assertThat(
                localResult?.results?.indices,
                Matchers.`is`(remoteResult.results.indices)
            )
        }
    }
}