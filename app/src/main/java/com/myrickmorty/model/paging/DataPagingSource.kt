package com.myrickmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myrickmorty.core.Constants.PAGE_INDEX
import com.myrickmorty.domain.Repository
import com.myrickmorty.model.data.RickMorty
import retrofit2.HttpException
import java.io.IOException

class DataPagingSource(private val repository: Repository) : PagingSource<Int, RickMorty>() {

    override fun getRefreshKey(state: PagingState<Int, RickMorty>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, RickMorty> {

        return try {
            val currentPage = params.key ?: PAGE_INDEX
            val response = repository.getAllCharacters(currentPage)
            val responseData = mutableListOf<RickMorty>()
            val data = response.results ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == PAGE_INDEX) null else -1,
                nextKey = currentPage.plus(1)
                //nextKey = if (responseData.isEmpty()) null else currentPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }
}