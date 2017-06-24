package io.github.pietrocaselani.moviestraker

import android.support.v4.app.FragmentManager
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
				.commitAllowingStateLoss()
	}
}