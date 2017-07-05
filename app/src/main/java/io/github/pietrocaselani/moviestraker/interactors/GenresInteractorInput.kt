package io.github.pietrocaselani.moviestraker.interactors

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
interface GenresInteractorInput {

	fun fetchGenres(): Flowable<GenreListResponse>

}