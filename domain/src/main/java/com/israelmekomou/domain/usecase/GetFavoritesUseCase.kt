package com.israelmekomou.domain.usecase

import androidx.paging.PagingData
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getFavorites()
}
