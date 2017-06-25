package io.github.pietrocaselani.moviestraker.ui.moviedetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.databinding.FragmentMovieDetailsBinding
import io.github.pietrocaselani.moviestraker.di.Injectable
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import javax.inject.Inject

/**
 * Created by pc on 24/06/17.
 */
class MovieDetailsFragment : Fragment(), Injectable {

	companion object {
		val TAG = "MovieDetailsFragment"
		val MOVIE_KEY = "Movie"
	}

	@Inject lateinit var viewModel: MovieDetailsViewModel

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val binding = DataBindingUtil.inflate<FragmentMovieDetailsBinding>(
				inflater,
				R.layout.fragment_movie_details,
				container,
				false
		)

		binding.viewModel = viewModel

		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		val movieEntity = arguments.getParcelable<MovieEntity>(MOVIE_KEY)

		viewModel.onStart(movieEntity)
	}
}