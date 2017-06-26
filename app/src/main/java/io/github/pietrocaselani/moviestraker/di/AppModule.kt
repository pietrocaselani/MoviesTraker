package io.github.pietrocaselani.moviestraker.di

import dagger.Module
import dagger.Provides
import io.github.pietrocaselani.moviestraker.BuildConfig
import io.github.pietrocaselani.moviestraker.MoviesTrackerApplication
import io.github.pietrocaselani.moviestraker.interactors.ConfigurationInteractor
import io.github.pietrocaselani.moviestraker.interactors.ConfigurationInteractorInput
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractor
import io.github.pietrocaselani.moviestraker.interactors.GenresInteractorInput
import io.github.pietrocaselani.moviestraker.repositories.ConfigurationInMemoryRepository
import io.github.pietrocaselani.moviestraker.repositories.ConfigurationRepository
import io.github.pietrocaselani.moviestraker.repositories.GenreInMemoryRepository
import io.github.pietrocaselani.moviestraker.repositories.GenreRepository
import io.github.pietrocaselani.moviestraker.ui.moviedetails.MovieDetailsModule
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import io.github.pietrocaselani.moviestraker.ui.searchmovies.SearchMoviesModule
import io.github.pietrocaselani.moviestraker.ui.upcoming.UpcomingModule
import javax.inject.Singleton

/**
 * Created by pc on 24/06/17.
 */
@Module(
		includes = arrayOf(
				UpcomingModule::class,
				MovieDetailsModule::class,
				SearchMoviesModule::class
		)
)
class AppModule(val application: MoviesTrackerApplication) {

	@Provides
	fun provideApplication(): MoviesTrackerApplication {
		return application
	}

	@Provides
	@Singleton
	fun providesTMDB(): TMDB {
		return TMDB(BuildConfig.TMDB_API_KEY)
	}

	@Provides
	@Singleton
	fun provideGenresInteractor(tmdb: TMDB, repository: GenreRepository): GenresInteractorInput {
		return GenresInteractor(tmdb, repository)
	}

	@Provides
	@Singleton
	fun provideConfigurationInteractor(tmdb: TMDB, repository: ConfigurationRepository): ConfigurationInteractorInput {
		return ConfigurationInteractor(tmdb, repository )
	}

	@Provides
	@Singleton
	fun provideGenreRepository(): GenreRepository {
		return GenreInMemoryRepository()
	}

	@Provides
	@Singleton
	fun provideConfigurationRepository(): ConfigurationRepository {
		return ConfigurationInMemoryRepository()
	}
}