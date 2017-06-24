package io.github.pietrocaselani.moviestraker.upcoming

/**
 * Created by pc on 24/06/17.
 */
data class MovieListViewModel(
		val name: String,
        val imageLink: String?,
        val releaseDate: String,
        val genres: List<String>
)