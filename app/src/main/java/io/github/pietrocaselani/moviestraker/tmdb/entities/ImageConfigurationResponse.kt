package io.github.pietrocaselani.moviestraker.tmdb.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 24/06/17.
 */
data class ImageConfigurationResponse(
		@SerializedName("secure_base_url") val secureBaseURL: String,
		@SerializedName("backdrop_sizes") val backdropSizes: List<String>,
		@SerializedName("poster_sizes") val posterSizes: List<String>
)