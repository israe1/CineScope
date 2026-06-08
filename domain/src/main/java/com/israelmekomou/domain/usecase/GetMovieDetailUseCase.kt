package com.israelmekomou.domain.usecase

import com.israelmekomou.domain.model.MovieDetail
import com.israelmekomou.domain.model.Result
import com.israelmekomou.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail> =
        repository.getMovieDetail(movieId)
}