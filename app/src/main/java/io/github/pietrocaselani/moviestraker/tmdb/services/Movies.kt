package io.github.pietrocaselani.moviestraker.tmdb.services

import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.PagedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by pc on 24/06/17.
 */
interface Movies {

	@GET("movie/upcoming")
	fun upcoming(@Query("page") page: Int): Flowable<PagedResponse<MovieResponse>>

}