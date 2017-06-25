package io.github.pietrocaselani.moviestraker.ui.upcoming

import dagger.Module
import dagger.Provides
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import javax.inject.Singleton

/**
 * Created by pc on 24/06/17.
 */
@Module
class UpcomingModule {

	@Provides
	@Singleton
	fun provideInteractor(tmdb: TMDB): UpcomingInteractorInput {
		return UpcomingInteractor(tmdb.movies(), tmdb.genres(), tmdb.configuration())
	}

	@Provides
	fun provideViewModel(interactor: UpcomingInteractorInput): UpcomingViewModel {
		return UpcomingViewModel(interactor)
	}

}