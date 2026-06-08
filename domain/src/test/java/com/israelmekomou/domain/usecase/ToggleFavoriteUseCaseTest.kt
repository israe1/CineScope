package com.israelmekomou.domain.usecase

import com.israelmekomou.domain.fake.FakeMovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ToggleFavoriteUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `invoking use case calls repository toggleFavorite`() = runTest {
        useCase(movieId = 1)
    }
}