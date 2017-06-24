package io.github.pietrocaselani.moviestraker.di

import dagger.Module
import dagger.Provides
import io.github.pietrocaselani.moviestraker.BuildConfig
import io.github.pietrocaselani.moviestraker.MoviesTrackerApplication
import io.github.pietrocaselani.moviestraker.moviedetails.MovieDetailsModule
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import io.github.pietrocaselani.moviestraker.upcoming.UpcomingModule
import javax.inject.Singleton

/**
 * Created by pc on 24/06/17.
 */
@Module(
		includes = arrayOf(
				UpcomingModule::class,
				MovieDetailsModule::class
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
}