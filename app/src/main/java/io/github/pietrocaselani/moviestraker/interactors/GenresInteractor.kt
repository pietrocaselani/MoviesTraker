package io.github.pietrocaselani.moviestraker.interactors

import io.github.pietrocaselani.moviestraker.repositories.GenreRepository
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by pc on 25/06/17.
 */
class GenresInteractor(tmdb: TMDB, private val repository: GenreRepository) : GenresInteractorInput {

	private val genresService = tmdb.genres()

	override fun fetchGenres(): Flowable<GenreListResponse> {
		val apiGenres = genresService.movieList().doOnNext { repository.saveGenres(it) }

		return repository.fetchGenres()
				.switchIfEmpty(apiGenres)
				.subscribeOn(Schedulers.io())
	}

}