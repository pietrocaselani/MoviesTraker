package io.github.pietrocaselani.moviestraker.tmdb.services

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by pc on 24/06/17.
 */
interface Genres {

	@GET("genre/movie/list")
	fun movieList(): Flowable<GenreListResponse>

}