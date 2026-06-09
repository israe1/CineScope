package com.israelmekomou.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.israelmekomou.domain.fake.FakeMovieRepository
import com.israelmekomou.domain.fake.MovieFixtures
import com.israelmekomou.domain.fake.collectPagingItems
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ToggleFavoriteUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `adds movie to favorites when not already favorited`() = runTest {
        repository.movies = listOf(MovieFixtures.movie)

        useCase(movieId = 1)

        val favorites = repository.getFavorites().collectPagingItems()
        assertThat(favorites).hasSize(1)
        assertThat(favorites.first().id).isEqualTo(1)
    }

    @Test
    fun `removes movie from favorites when already favorited`() = runTest {
        repository.seedFavorite(MovieFixtures.movie)

        useCase(movieId = 1)

        val favorites = repository.getFavorites().collectPagingItems()
        assertThat(favorites).isEmpty()
    }

    @Test
    fun `does nothing when movie id is unknown`() = runTest {
        useCase(movieId = 99)

        val favorites = repository.getFavorites().collectPagingItems()
        assertThat(favorites).isEmpty()
    }
}
