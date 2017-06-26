package io.github.pietrocaselani.moviestraker.repositories

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
interface GenreRepository {

	fun fetchGenres(): Flowable<GenreListResponse>

	fun saveGenres(genres: GenreListResponse)

}