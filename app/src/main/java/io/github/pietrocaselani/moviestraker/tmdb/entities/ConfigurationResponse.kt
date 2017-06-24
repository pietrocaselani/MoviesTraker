package io.github.pietrocaselani.moviestraker.tmdb.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 24/06/17.
 */
data class ConfigurationResponse(
		@SerializedName("images") val imageConfiguration: ImageConfigurationResponse
)