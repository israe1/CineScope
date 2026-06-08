package com.israelmekomou.domain.usecase

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(query: String): Flow<PagingData<Movie>> =
        repository.searchMovies(query)
}