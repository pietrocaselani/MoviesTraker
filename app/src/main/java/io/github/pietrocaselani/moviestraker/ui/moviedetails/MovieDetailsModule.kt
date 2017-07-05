package io.github.pietrocaselani.moviestraker.ui.moviedetails

import dagger.Module
import dagger.Provides

/**
 * Created by pc on 24/06/17.
 */
@Module
class MovieDetailsModule {

	@Provides
	fun provideViewModel(): MovieDetailsViewModel {
		return MovieDetailsViewModel()
	}

}