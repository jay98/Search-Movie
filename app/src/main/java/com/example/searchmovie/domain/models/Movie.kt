package com.example.searchmovie.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val backdropPath: String,
    val posterPath: String
) : Parcelable
