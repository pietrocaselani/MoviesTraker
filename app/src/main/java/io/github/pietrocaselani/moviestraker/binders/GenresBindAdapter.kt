package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.widget.TextView
import io.github.pietrocaselani.moviestraker.entities.GenreEntity

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("genres")
fun bindGenres(textView: TextView, genres: List<GenreEntity>) {
	val text = genres.joinToString {
		it.name
	}
	textView.text = text
}