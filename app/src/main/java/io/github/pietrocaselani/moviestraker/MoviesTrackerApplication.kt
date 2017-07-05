package io.github.pietrocaselani.moviestraker

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.github.pietrocaselani.moviestraker.di.AppInjector
import javax.inject.Inject

/**
 * Created by pc on 24/06/17.
 */
class MoviesTrackerApplication: Application(), HasActivityInjector {

	@Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

	override fun onCreate() {
		super.onCreate()

		AppInjector.init(this)
	}

	override fun activityInjector(): AndroidInjector<Activity> {
		return dispatchingAndroidInjector
	}
}