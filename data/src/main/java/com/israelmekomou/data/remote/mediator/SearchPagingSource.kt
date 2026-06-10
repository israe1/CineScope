package com.israelmekomou.data.remote.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.israelmekomou.data.local.entity.MovieEntity
import com.israelmekomou.data.local.mapper.toEntity
import com.israelmekomou.data.remote.api.TmdbApi
import okio.IOException
import retrofit2.HttpException

class SearchPagingSource(
    private val api: TmdbApi,
    private val query: String
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            val page = params.key ?: 1
            val response = api.searchMovies(query, page)
            val entities = response.results.map { it.toEntity(page) }

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
}