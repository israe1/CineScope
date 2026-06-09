package com.israelmekomou.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.israelmekomou.domain.fake.FakeMovieRepository
import com.israelmekomou.domain.fake.MovieFixtures
import com.israelmekomou.domain.fake.collectPagingItems
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = GetMoviesUseCase(repository)

    @Test
    fun `returns empty list when repository has no movies`() = runTest {
        val movies = useCase().collectPagingItems()

        assertThat(movies).isEmpty()
    }

    @Test
    fun `returns movies from repository`() = runTest {
        repository.movies = listOf(MovieFixtures.movie)

        val movies = useCase().collectPagingItems()

        assertThat(movies).hasSize(1)
        assertThat(movies.first().title).isEqualTo("Inception")
    }
}
