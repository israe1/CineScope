package com.israelmekomou.domain.fake

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail
import com.israelmekomou.domain.model.Result
import com.israelmekomou.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository : MovieRepository {

    private val favorites = mutableListOf<Movie>()
    var shouldReturnError = false
    var movieDetail: MovieDetail? = null
    var movies: List<Movie> = emptyList()

    override fun getMovies(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(movies))

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        if (shouldReturnError) return Result.Error(Exception("Network error"))
        return movieDetail?.let { Result.Success(it) }
            ?: Result.Error(Exception("Not found"))
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val existing = favorites.find { it.id == movieId }
        if (existing != null) {
            favorites.remove(existing)
        } else {
            movies.find { it.id == movieId }?.let { favorites.add(it) }
        }
    }

    override fun getFavorites(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(favorites.toList()))

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        if (query.isBlank()) return flowOf(PagingData.from(emptyList()))
        val results = movies.filter { it.title.contains(query, ignoreCase = true) }
        return flowOf(PagingData.from(results))
    }

    fun seedFavorite(movie: Movie) {
        if (favorites.none { it.id == movie.id }) {
            favorites.add(movie)
        }
    }
}
