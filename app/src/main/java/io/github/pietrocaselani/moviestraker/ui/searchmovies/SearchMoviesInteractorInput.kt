package io.github.pietrocaselani.moviestraker.ui.searchmovies

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.PagedResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
interface SearchMoviesInteractorInput {

	fun searchMovies(query: String, page: Int): Flowable<PagedResponse<MovieResponse>>

	fun fetchGenres(): Flowable<GenreListResponse>

	fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse>

}