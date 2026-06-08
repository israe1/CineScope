package com.israelmekomou.domain.fake

import com.israelmekomou.domain.model.CastMember
import com.israelmekomou.domain.model.Genre
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail

object MovieFixtures {
    val movie = Movie(
        id = 1,
        title = "Inception",
        overview = "A thief who steals corporate secrets...",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        rating = 8.8,
        releaseDate = "2010-07-16",
        genres = listOf(Genre(28, "Action"), Genre(878, "Science Fiction"))
    )

    val movieDetail = MovieDetail(
        id = 1,
        title = "Inception",
        overview = "A thief who steals corporate secrets...",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        rating = 8.8,
        releaseDate = "2010-07-16",
        runtime = 148,
        genres = listOf(Genre(28, "Action")),
        cast = listOf(CastMember(1, "Leonardo DiCaprio", "Cobb", null))
    )
}