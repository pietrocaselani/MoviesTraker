package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import io.github.pietrocaselani.moviestraker.ui.common.MovieListViewModel
import io.github.pietrocaselani.moviestraker.ui.common.MoviesAdapter

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("movies")
fun bindArticles(recyclerView: RecyclerView, movies: List<MovieListViewModel>) {
	if (recyclerView.adapter is MoviesAdapter) {
		val moviesAdapter = recyclerView.adapter as MoviesAdapter
		moviesAdapter.setMovies(movies)
	} else {
		val adapter = MoviesAdapter(movies)
		recyclerView.adapter = adapter
	}

}