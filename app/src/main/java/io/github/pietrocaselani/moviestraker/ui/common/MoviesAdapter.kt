package io.github.pietrocaselani.moviestraker.ui.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.databinding.MovieListItemBinding

/**
 * Created by pc on 24/06/17.
 */
class MoviesAdapter(private var movies: List<MovieListViewModel>) : RecyclerView.Adapter<MovieViewHolder>() {

	private var currentSize = movies.size

	fun setMovies(newMovies: List<MovieListViewModel>) {
		movies = newMovies

		notifyDataSetChanged()

		currentSize = movies.size
	}

	override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
		val movie = movies[position]
		holder?.bind(movie)
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
		val context = parent!!.context
		val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
		return MovieViewHolder(view)
	}

	override fun getItemCount(): Int {
		return movies.size
	}

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val binding: MovieListItemBinding = MovieListItemBinding.bind(itemView)

	fun bind(movieViewModel: MovieListViewModel) = with(itemView) {
		binding.viewModel = movieViewModel
	}

}