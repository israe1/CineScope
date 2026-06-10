package com.israelmekomou.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.israelmekomou.data.local.dao.MovieDao
import com.israelmekomou.data.local.entity.MovieEntity
import com.israelmekomou.data.local.mapper.toEntity
import com.israelmekomou.data.remote.api.TmdbApi
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: TmdbApi,
    private val dao: MovieDao
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastPage = dao.getLastPage() ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )
                    lastPage + 1
                }
            }

            val response = api.getPopularMovies(page = page)
            val movies = response.results.map { it.toEntity(page) }

            if (loadType == LoadType.REFRESH) {
                dao.clearNonFavoriteMovies()
            }
            dao.upsertMovies(movies)

            MediatorResult.Success(
                endOfPaginationReached = page >= response.totalPages
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}