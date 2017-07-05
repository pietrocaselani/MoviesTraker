package io.github.pietrocaselani.moviestraker.repositories

import io.github.pietrocaselani.moviestraker.tmdb.entities.ConfigurationResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
interface ConfigurationRepository {

	fun saveConfiguration(configuration: ConfigurationResponse)

	fun fetchConfiguration(): Flowable<ConfigurationResponse>

}