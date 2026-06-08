package com.israelmekomou.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Double,
    val releaseDate: String,
    val runtime: Int?,
    val genres: List<Genre>,
    val cast: List<CastMember>
)
