package io.github.pietrocaselani.moviestraker.ui.upcoming

import io.github.pietrocaselani.moviestraker.interactors.ConfigurationInteractorInput
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractor
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractorInput
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.PagedResponse
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
		private val genresInteractor: GenresInteractorInput,
		private val configurationInteractor: ConfigurationInteractorInput
) : UpcomingInteractorInput {

	override fun fetchUpcomingMovies(page: Int): Flowable<PagedResponse<MovieResponse>> {
		return moviesService.upcoming(page).subscribeOn(Schedulers.io())
	}

	override fun fetchGenres(): Flowable<GenreListResponse> {
		return genresInteractor.fetchGenres()
	}

	override fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse> {
		return configurationInteractor.fetchImageConfigurations()
	}
}