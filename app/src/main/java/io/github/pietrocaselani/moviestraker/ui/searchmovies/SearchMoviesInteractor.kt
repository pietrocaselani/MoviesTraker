package io.github.pietrocaselani.moviestraker.ui.searchmovies

import io.github.pietrocaselani.moviestraker.interactors.ConfigurationInteractorInput
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractorInput
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.PagedResponse
import io.github.pietrocaselani.moviestraker.tmdb.services.Configuration
import io.github.pietrocaselani.moviestraker.tmdb.services.Genres
import io.github.pietrocaselani.moviestraker.tmdb.services.Search
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by pc on 25/06/17.
 */
class SearchMoviesInteractor(
		private val searchService: Search,
		private val genresInteractor: GenresInteractorInput,
		private val configurationInteractor: ConfigurationInteractorInput
) : SearchMoviesInteractorInput {

	override fun fetchGenres(): Flowable<GenreListResponse> {
		return genresInteractor.fetchGenres()
	}

	override fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse> {
		return configurationInteractor.fetchImageConfigurations()
	}

	override fun searchMovies(query: String, page: Int): Flowable<PagedResponse<MovieResponse>> {
		return searchService.searchMovie(query, page).subscribeOn(Schedulers.io())
	}

}