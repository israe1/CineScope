package com.israelmekomou.domain.usecase

import com.israelmekomou.domain.repository.MovieRepository

class ToggleFavoriteUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) =
        repository.toggleFavorite(movieId)
}