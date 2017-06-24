package io.github.pietrocaselani.moviestraker.tmdb.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 24/06/17.
 */
data class UpcomingResponse(
		val page: Int,
		@SerializedName("results") val movies: List<MovieResponse>,
        @SerializedName("total_pages") val totalPages: Int
)