package io.github.pietrocaselani.moviestraker.tmdb.entities

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by pc on 24/06/17.
 */
data class MovieResponse(
		val id: Int,
		val title: String,
		val overview: String,
		@SerializedName("poster_path") val posterPath: String?,
		@SerializedName("backdrop_path") val backdropPath: String?,
		@SerializedName("release_date") val releaseDate: Date,
		@SerializedName("genre_ids") val genreIds: List<Int>
)