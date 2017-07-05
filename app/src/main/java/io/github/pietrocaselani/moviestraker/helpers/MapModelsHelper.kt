package io.github.pietrocaselani.moviestraker.helpers

import io.github.pietrocaselani.moviestraker.entities.GenreEntity
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.ui.common.MovieListViewModel
import okhttp3.HttpUrl
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 25/06/17.
 */
fun mapToMovieEntity(movies: List<MovieResponse>, genres: List<GenreResponse>,
                     imageConfiguration: ImageConfigurationResponse?): List<MovieEntity> {
	return movies.map { movie ->
		val movieGenres = genres.filter {
			movie.genreIds.contains(it.id)
		}.map {
			GenreEntity(it.id, it.name)
		}

		val basePath = imageConfiguration?.let {
			val sizePath = it.posterSizes.first()
			it.secureBaseURL + sizePath
		}

		val posterLink = basePath?.plus(movie.posterPath)
		val backdropLink = basePath?.plus(movie.backdropPath)

		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

		val releaseDate: Date

		releaseDate = try {
			dateFormat.parse(movie.releaseDate)
		} catch (e: ParseException) {
			Date(0)
		}

		MovieEntity(
				id = movie.id,
				title = movie.title,
				overview = movie.overview,
				posterLink = posterLink,
				backdropLink = backdropLink,
				releaseDate = releaseDate,
				genres = movieGenres
		)
	}
}

fun mapToMovieListViewModel(moviesEntity: List<MovieEntity>): List<MovieListViewModel> {
	val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM)

	return moviesEntity.map { movieEntity ->
		val movieGenres = movieEntity.genres.map {
			it.name
		}

		val imageLink: String?

		if (movieEntity.posterLink != null) {
			imageLink = movieEntity.posterLink
		} else if (movieEntity.backdropLink != null) {
			imageLink = movieEntity.backdropLink
		} else {
			imageLink = null
		}

		MovieListViewModel(
				name = movieEntity.title,
				imageLink = imageLink,
				genres = movieGenres,
				releaseDate = dateFormat.format(movieEntity.releaseDate)
		)
	}
}

fun mapMovieToDetails(movie: MovieEntity, imageConfiguration: ImageConfigurationResponse?): MovieEntity {
	val basePath = imageConfiguration?.let {
		var sizePath = it.posterSizes.find {
			it.contains("w3")
		}

		sizePath = sizePath ?: it.posterSizes.last()

		it.secureBaseURL + sizePath
	}

	var biggerPosterLink: String? = null
	var biggerBackdropLink: String? = null

	if (movie.posterLink != null) {
		val posterLink = HttpUrl.parse(movie.posterLink)?.pathSegments()?.last()
		biggerPosterLink = HttpUrl.parse(basePath)?.newBuilder()
				?.addPathSegment(posterLink)
				?.build()
				?.toString()
	} else if (movie.backdropLink != null) {
		val backdropLink = HttpUrl.parse(movie.backdropLink)?.pathSegments()?.last()
		biggerBackdropLink = HttpUrl.parse(basePath)?.newBuilder()
				?.addPathSegment(backdropLink)
				?.build()
				?.toString()
	}

	return MovieEntity(
			id = movie.id,
			title = movie.title,
			overview = movie.overview,
			releaseDate = movie.releaseDate,
			genres = movie.genres,
			posterLink = biggerPosterLink,
			backdropLink = biggerBackdropLink
	)
}