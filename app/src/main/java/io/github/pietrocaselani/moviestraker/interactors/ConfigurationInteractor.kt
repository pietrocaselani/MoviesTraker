package io.github.pietrocaselani.moviestraker.interactors

import io.github.pietrocaselani.moviestraker.repositories.ConfigurationRepository
import io.github.pietrocaselani.moviestraker.tmdb.TMDB
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by pc on 25/06/17.
 */
class ConfigurationInteractor(tmdb: TMDB, private val repository: ConfigurationRepository) : ConfigurationInteractorInput {

	private val configurationService = tmdb.configuration()

	override fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse> {
		val apiConfiguration = configurationService.configuration()
				.doOnNext { repository.saveConfiguration(it) }

		return repository.fetchConfiguration()
				.switchIfEmpty(apiConfiguration)
				.map { it.imageConfiguration }
				.subscribeOn(Schedulers.io())
	}
}