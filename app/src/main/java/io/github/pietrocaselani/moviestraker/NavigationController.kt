package io.github.pietrocaselani.moviestraker

import android.os.Bundle
import android.support.v4.app.FragmentManager
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import io.github.pietrocaselani.moviestraker.moviedetails.MovieDetailsFragment
import io.github.pietrocaselani.moviestraker.upcoming.UpcomingFragment
import javax.inject.Inject

/**
 * Created by pc on 24/06/17.
 */
class NavigationController private constructor(
		private val containerId: Int,
		private val fragmentManager: FragmentManager
) {

	@Inject constructor(activity: MainActivity) :
			this(R.id.root_container, activity.supportFragmentManager)

	fun navigateToUpcomingMovies() {
		val upcomingFragment = UpcomingFragment()
		fragmentManager.beginTransaction()
				.add(containerId, upcomingFragment, UpcomingFragment.TAG)
				.commit()
	}

	fun navigateToMovieDetails(movie: MovieEntity) {
		val movieDetailsFragment = MovieDetailsFragment()
		val args = Bundle(1)
		args.putParcelable(MovieDetailsFragment.MOVIE_KEY, movie)
		movieDetailsFragment.arguments = args

		fragmentManager.beginTransaction()
				.addToBackStack(null)
				.replace(containerId, movieDetailsFragment, MovieDetailsFragment.TAG)
				.commit()
	}
}