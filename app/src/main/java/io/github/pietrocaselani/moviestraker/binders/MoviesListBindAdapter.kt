package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import io.github.pietrocaselani.moviestraker.upcoming.MovieListViewModel
import io.github.pietrocaselani.moviestraker.upcoming.MoviesAdapter
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("movies")
fun bindArticles(recyclerView: RecyclerView, movies: List<MovieListViewModel>) {
	if (recyclerView.adapter is MoviesAdapter) {
		val moviesAdapter = recyclerView.adapter as MoviesAdapter
		moviesAdapter.addMovies(movies)
	} else {
		val adapter = MoviesAdapter(movies)
		adapter.setHasStableIds(true)
		recyclerView.itemAnimator = SlideInDownAnimator()
		recyclerView.adapter = adapter
	}

}