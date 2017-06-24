package io.github.pietrocaselani.moviestraker.moviedetails

import android.databinding.BaseObservable
import android.databinding.ObservableField
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.entities.GenreEntity
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import java.text.SimpleDateFormat

/**
 * Created by pc on 24/06/17.
 */
class MovieDetailsViewModel : BaseObservable() {

	val title = ObservableField<String>()
	val overview = ObservableField<String>()
	val releaseDate = ObservableField<String>()
	val posterLink = ObservableField<String?>()
	val genres = ObservableField<List<GenreEntity>>(emptyList())

	val darkMutedColor = ObservableField<Int>(R.color.colorPrimaryDark)
	val darkVibrantColor = ObservableField<Int>(R.color.colorPrimaryDark)
	val vibrantColor = ObservableField<Int>(R.color.colorAccent)
	val lightVibrantColor = ObservableField<Int>(android.R.color.white)

	fun onStart(movie: MovieEntity) {
		if (movie.posterLink != null) {
			posterLink.set(movie.posterLink)
		} else {
			posterLink.set(movie.backdropLink)
		}

		title.set(movie.title)
		overview.set(movie.overview)

		val date = SimpleDateFormat
				.getDateInstance(SimpleDateFormat.SHORT)
				.format(movie.releaseDate)

		releaseDate.set(date)
		genres.set(movie.genres)
	}

}