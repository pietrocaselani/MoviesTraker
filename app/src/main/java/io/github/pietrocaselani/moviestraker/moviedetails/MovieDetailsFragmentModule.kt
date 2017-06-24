package io.github.pietrocaselani.moviestraker.moviedetails

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by pc on 24/06/17.
 */
@Module
abstract class MovieDetailsFragmentModule {

	@ContributesAndroidInjector
	abstract fun contributMovieDetailsFragment(): MovieDetailsFragment

}