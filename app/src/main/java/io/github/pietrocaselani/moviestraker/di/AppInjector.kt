package io.github.pietrocaselani.moviestraker.di

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.github.pietrocaselani.moviestraker.ActivityLifecycleCallbacksAdapter
import io.github.pietrocaselani.moviestraker.MoviesTrackerApplication
import io.github.pietrocaselani.moviestraker.ui.moviedetails.MovieDetailsModule
import io.github.pietrocaselani.moviestraker.ui.searchmovies.SearchMoviesModule
import io.github.pietrocaselani.moviestraker.ui.upcoming.UpcomingModule

/**
 * Created by pc on 24/06/17.
 */
object AppInjector {

	fun init(application: MoviesTrackerApplication) {
		val moviesTrackerComponent = DaggerMoviesTrackerComponent.builder()
				.appModule(AppModule(application))
				.upcomingModule(UpcomingModule())
				.movieDetailsModule(MovieDetailsModule())
				.searchMoviesModule(SearchMoviesModule())
				.build()

		moviesTrackerComponent.inject(application)

		application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
			override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
				handleActivity(activity)
			}
		})
	}

	private fun handleActivity(activity: Activity?) {
		if (activity is HasSupportFragmentInjector) {
			AndroidInjection.inject(activity)
		}

		if (activity is FragmentActivity) {
			val fragmentsCallback = object : FragmentLifecycleCallbacks() {
				override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
					if (f is Injectable) {
						AndroidSupportInjection.inject(f)
					}
				}
			}

			activity.supportFragmentManager.
					registerFragmentLifecycleCallbacks(fragmentsCallback, true)
		}
	}

}