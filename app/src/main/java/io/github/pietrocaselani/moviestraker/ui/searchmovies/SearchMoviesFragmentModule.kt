package io.github.pietrocaselani.moviestraker.ui.searchmovies

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by pc on 25/06/17.
 */
@Module
abstract class SearchMoviesFragmentModule {

	@ContributesAndroidInjector
	abstract fun contributesSearchMoviesFragment(): SearchMoviesFragment

}