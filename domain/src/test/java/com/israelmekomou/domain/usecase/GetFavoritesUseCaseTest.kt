package com.israelmekomou.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.israelmekomou.domain.fake.FakeMovieRepository
import com.israelmekomou.domain.fake.MovieFixtures
import com.israelmekomou.domain.fake.collectPagingItems
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetFavoritesUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = GetFavoritesUseCase(repository)

    @Test
    fun `returns empty list when no favorites exist`() = runTest {
        val favorites = useCase().collectPagingItems()

        assertThat(favorites).isEmpty()
    }

    @Test
    fun `returns seeded favorites`() = runTest {
        repository.seedFavorite(MovieFixtures.movie)

        val favorites = useCase().collectPagingItems()

        assertThat(favorites).hasSize(1)
        assertThat(favorites.first().title).isEqualTo("Inception")
    }
}
