package io.github.pietrocaselani.moviestraker.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.pietrocaselani.moviestraker.ui.MainActivity
import io.github.pietrocaselani.moviestraker.ui.moviedetails.MovieDetailsFragmentModule
import io.github.pietrocaselani.moviestraker.ui.searchmovies.SearchMoviesFragmentModule
import io.github.pietrocaselani.moviestraker.ui.upcoming.UpcomingFragmentModule

/**
 * Created by pc on 24/06/17.
 */
@Module
abstract class MainActivityModule {

	@ContributesAndroidInjector(modules = arrayOf(
			UpcomingFragmentModule::class,
			MovieDetailsFragmentModule::class,
			SearchMoviesFragmentModule::class
	))
	abstract fun contributeMainActivity(): MainActivity
}