package com.israelmekomou.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.israelmekomou.data.local.dao.MovieDao
import com.israelmekomou.data.local.mapper.toDomain
import com.israelmekomou.data.remote.api.TmdbApi
import com.israelmekomou.data.remote.mediator.MovieRemoteMediator
import com.israelmekomou.data.remote.mediator.SearchPagingSource
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail
import com.israelmekomou.domain.model.Result
import com.israelmekomou.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val dao: MovieDao
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = MovieRemoteMediator(api, dao),
        pagingSourceFactory = { dao.getMoviesPaged() }
    ).flow.map { pagingData -> pagingData.map { it.toDomain() } }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val detail = api.getMovieDetail(movieId)
            Result.Success(detail.toDomain())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        dao.toggleFavorite(movieId)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavorites(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { dao.getFavoritesPaged() }
    ).flow.map { pagingData -> pagingData.map { it.toDomain() } }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> =  Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            SearchPagingSource(api, query)
        }
    ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
}