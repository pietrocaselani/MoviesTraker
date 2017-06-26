package io.github.pietrocaselani.moviestraker.repositories

import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreListResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
class GenreInMemoryRepository: GenreRepository {

	private var genres: GenreListResponse? = null

	override fun saveGenres(genres: GenreListResponse) {
		this.genres = genres
	}

	override fun fetchGenres(): Flowable<GenreListResponse> {
		val immutableGenres = genres
		if (immutableGenres != null) {
			return Flowable.just(immutableGenres)
		}

		return Flowable.empty()
	}
}