package io.github.pietrocaselani.moviestraker.interactors

import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.reactivex.Flowable

/**
 * Created by pc on 25/06/17.
 */
interface ConfigurationInteractorInput {

	fun fetchImageConfigurations(): Flowable<ImageConfigurationResponse>
}