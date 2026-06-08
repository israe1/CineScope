package com.israelmekomou.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.israelmekomou.domain.fake.FakeMovieRepository
import com.israelmekomou.domain.fake.MovieFixtures
import com.israelmekomou.domain.model.Result
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMovieDetailUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = GetMovieDetailUseCase(repository)

    @Test
    fun `returns success when repository has movie detail`() = runTest {
        repository.movieDetail = MovieFixtures.movieDetail

        val result = useCase(movieId = 1)

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.title).isEqualTo("Inception")
    }

    @Test
    fun `returns error when repository signals error`() = runTest {
        repository.shouldReturnError = true

        val result = useCase(movieId = 1)

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun `returns error when movie detail is not found`() = runTest {
        repository.movieDetail = null
        repository.shouldReturnError = false

        val result = useCase(movieId = 99)

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }
}