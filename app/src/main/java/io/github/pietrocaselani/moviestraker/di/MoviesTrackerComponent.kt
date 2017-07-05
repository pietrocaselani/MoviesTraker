package io.github.pietrocaselani.moviestraker.di

import dagger.Component
import io.github.pietrocaselani.moviestraker.MoviesTrackerApplication
import javax.inject.Singleton

/**
 * Created by pc on 24/06/17.
 */
@Singleton
@Component(modules = arrayOf(
		AppModule::class,
		MainActivityModule::class
))
interface MoviesTrackerComponent {

	fun inject(application: MoviesTrackerApplication)
	
}