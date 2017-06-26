package io.github.pietrocaselani.moviestraker.tmdb.services

import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.PagedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by pc on 25/06/17.
 */
interface Search {

	@GET("search/movie")
	fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Flowable<PagedResponse<MovieResponse>>

}