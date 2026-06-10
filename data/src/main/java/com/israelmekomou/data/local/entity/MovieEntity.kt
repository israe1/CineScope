package com.israelmekomou.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Double,
    val releaseDate: String,
    val genreIds: String,      // comma-separated, e.g. "28,12,878"
    val isFavorite: Boolean = false,
    val page: Int              // which page this item came from
)