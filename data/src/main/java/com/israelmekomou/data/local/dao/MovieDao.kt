package com.israelmekomou.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.israelmekomou.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY page ASC")
    fun getMoviesPaged(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoritesPaged(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Upsert
    suspend fun upsertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun toggleFavorite(movieId: Int)

    @Query("DELETE FROM movies WHERE isFavorite = 0")
    suspend fun clearNonFavoriteMovies()

    @Query("SELECT MAX(page) FROM movies")
    suspend fun getLastPage(): Int?
}