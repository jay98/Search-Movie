package com.example.searchmovie.data.remote.models

import com.example.searchmovie.domain.models.Config
import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("images") val images: ImageConfigResponse,
) {

    companion object {
        private const val POSTER_IMAGE_INDEX = 2
        private const val POSTER_DEFAULT_SIZE = "w154"

        // Ideally these would be done in specific transformers and those transformers
        // would be unit tested but to keep things simple going with this pattern
        fun ConfigResponse.toConfig(): Config {
            return Config(
                imageBaseUrl = images.secureBaseUrl,
                posterImageSize = images.posterSizes.getOrElse(POSTER_IMAGE_INDEX) {
                    POSTER_DEFAULT_SIZE
                },
                backDropSize = images.backdropSizes.last() // assuming part of a streaming app so internet
                // should be pretty fast and expected to be on
                //wifi so loading original image shouldn't be that
                //much of problem
            )
        }
    }
}




