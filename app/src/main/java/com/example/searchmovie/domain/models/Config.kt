package com.example.searchmovie.domain.models

import com.google.gson.Gson

data class Config(
    val imageBaseUrl: String,
    val backDropSize: String,
    val posterImageSize: String
)
