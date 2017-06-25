package io.github.pietrocaselani.moviestraker.ui.upcoming

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.UpcomingResponse
import io.github.pietrocaselani.moviestraker.tmdb.services.Configuration
import io.github.pietrocaselani.moviestraker.tmdb.services.Genres
import io.github.pietrocaselani.moviestraker.tmdb.services.Movies
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by pc on 24/06/17.
 */
class UpcomingInteractor(
		private val moviesService: Movies,
		private val genresService: Genres,
		private val configurationService: Configuration
) : UpcomingInteractorInput {

	override fun fetchUpcomingMovies(page: Int): Flowable<UpcomingResponse> {
		return moviesService.upcoming(page).subscribeOn(Schedulers.io())
	}

	override fun fetchGenres(): Flowable<GenreListResponse> {
		return genresService.movieList().subscribeOn(Schedulers.io())
	}

	override fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse> {
		return configurationService.configuration()
				.map { it.imageConfiguration }
				.subscribeOn(Schedulers.io())
	}
}