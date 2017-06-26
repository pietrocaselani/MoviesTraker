package io.github.pietrocaselani.moviestraker.repositories

import io.github.pietrocaselani.moviestraker.tmdb.entities.ConfigurationResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
class ConfigurationInMemoryRepository: ConfigurationRepository {

	private var configuration: ConfigurationResponse? = null

	override fun saveConfiguration(configuration: ConfigurationResponse) {
		this.configuration = configuration
	}

	override fun fetchConfiguration(): Flowable<ConfigurationResponse> {
		val immutableConfiguration = configuration
		if (immutableConfiguration != null) {
			return Flowable.just(immutableConfiguration)
		}

		return Flowable.empty()
	}
}