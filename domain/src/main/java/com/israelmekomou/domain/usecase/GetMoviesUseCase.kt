package com.israelmekomou.domain.usecase

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getMovies()
}