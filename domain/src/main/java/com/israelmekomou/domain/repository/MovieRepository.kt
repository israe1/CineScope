package com.israelmekomou.domain.repository

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail
import com.israelmekomou.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
    suspend fun toggleFavorite(movieId: Int)
    fun getFavorites(): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}