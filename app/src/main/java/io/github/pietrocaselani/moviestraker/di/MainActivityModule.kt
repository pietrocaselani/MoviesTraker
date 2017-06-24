package io.github.pietrocaselani.moviestraker.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.pietrocaselani.moviestraker.MainActivity
import io.github.pietrocaselani.moviestraker.upcoming.UpcomingFragmentModule

/**
 * Created by pc on 24/06/17.
 */
@Module
abstract class MainActivityModule {

	@ContributesAndroidInjector(modules = arrayOf(
			UpcomingFragmentModule::class
	))
	abstract fun contributeMainActivity(): MainActivity
}