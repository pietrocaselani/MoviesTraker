package io.github.pietrocaselani.moviestraker.ui.searchmovies

import dagger.Module
import dagger.Provides
import io.github.pietrocaselani.moviestraker.interactors.ConfigurationInteractorInput
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractorInput
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import javax.inject.Singleton

/**
 * Created by pc on 25/06/17.
 */
@Module
class SearchMoviesModule {

	@Provides
	@Singleton
	fun provideInteractor(tmdb: TMDB,
	                      genresInteractorInput: GenresInteractorInput,
	                      configurationInteractorInput: ConfigurationInteractorInput): SearchMoviesInteractorInput {
		return SearchMoviesInteractor(tmdb.search(), genresInteractorInput, configurationInteractorInput)
	}

	@Provides
	fun provideViewModel(interactor: SearchMoviesInteractorInput): SearchMoviesViewModel {
		return SearchMoviesViewModel(interactor)
	}

}