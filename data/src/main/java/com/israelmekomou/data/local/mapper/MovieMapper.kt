package com.israelmekomou.data.local.mapper

import com.israelmekomou.data.local.entity.MovieEntity
import com.israelmekomou.data.remote.dto.MovieDetailDto
import com.israelmekomou.data.remote.dto.MovieDto
import com.israelmekomou.domain.model.CastMember
import com.israelmekomou.domain.model.Genre
import com.israelmekomou.domain.model.Movie
import com.israelmekomou.domain.model.MovieDetail

fun MovieDto.toEntity(page: Int): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = voteAverage,
    releaseDate = releaseDate,
    genreIds = genreIds.joinToString(","),
    page = page
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = rating,
    releaseDate = releaseDate,
    genres = genreIds
        .split(",")
        .filter { it.isNotEmpty() }
        .map { Genre(it.trim().toInt(), "") }
)

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = voteAverage,
    releaseDate = releaseDate,
    runtime = runtime,
    genres = genres.map { Genre(it.id, it.name) },
    cast = credits?.cast?.map {
        CastMember(it.id, it.name, it.character, it.profilePath)
    } ?: emptyList()
)