package com.israelmekomou.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.israelmekomou.domain.fake.FakeMovieRepository
import com.israelmekomou.domain.fake.MovieFixtures
import com.israelmekomou.domain.fake.collectPagingItems
import com.israelmekomou.domain.model.Genre
import com.israelmekomou.domain.model.Movie
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SearchMoviesUseCaseTest {

    private val repository = FakeMovieRepository()
    private val useCase = SearchMoviesUseCase(repository)

    private val catalog = listOf(
        MovieFixtures.movie,
        Movie(
            id = 2,
            title = "The Matrix",
            overview = "A computer hacker learns about the true nature of reality.",
            posterPath = "/matrix.jpg",
            backdropPath = "/matrix_backdrop.jpg",
            rating = 8.7,
            releaseDate = "1999-03-31",
            genres = listOf(Genre(28, "Action"))
        )
    )

    @Test
    fun `returns empty list for blank query`() = runTest {
        repository.movies = catalog

        val results = useCase(query = "  ").collectPagingItems()

        assertThat(results).isEmpty()
    }

    @Test
    fun `returns matching movies for query`() = runTest {
        repository.movies = catalog

        val results = useCase(query = "Inception").collectPagingItems()

        assertThat(results).hasSize(1)
        assertThat(results.first().title).isEqualTo("Inception")
    }

    @Test
    fun `returns empty list when no movies match query`() = runTest {
        repository.movies = catalog

        val results = useCase(query = "Unknown").collectPagingItems()

        assertThat(results).isEmpty()
    }
}
