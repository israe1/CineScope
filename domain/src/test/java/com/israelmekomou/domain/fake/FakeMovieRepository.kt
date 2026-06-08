package com.israelmekomou.domain.fake

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail
import com.israelmekomou.domain.repository.MovieRepository
import com.israelmekomou.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository : MovieRepository {

    private val favorites = mutableListOf<Movie>()
    var shouldReturnError = false
    var movieDetail: MovieDetail? = null

    override fun getMovies(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(emptyList()))

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        if (shouldReturnError) return Result.Error(Exception("Network error"))
        return movieDetail?.let { Result.Success(it) }
            ?: Result.Error(Exception("Not found"))
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val existing = favorites.find { it.id == movieId }
        if (existing != null) favorites.remove(existing)
        // else add it
    }

    override fun getFavorites(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(favorites))

    override fun searchMovies(query: String): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(emptyList()))
}