package io.github.pietrocaselani.moviestraker.tmdb.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 24/06/17.
 */
data class PagedResponse<T>(
		val page: Int,
		@SerializedName("results") val results: List<T>,
		@SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
)