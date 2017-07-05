package io.github.pietrocaselani.moviestraker.tmdb

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.github.pietrocaselani.moviestraker.tmdb.services.Configuration
import io.github.pietrocaselani.moviestraker.tmdb.services.Genres
import io.github.pietrocaselani.moviestraker.tmdb.services.Movies
import io.github.pietrocaselani.moviestraker.tmdb.services.Search
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by pc on 24/06/17.
 */
open class TMDB(private val apiKey: String) {

	companion object {
		val API_HOST = "api.themoviedb.org"
		val API_VERSION = "3"
		val API_URL = "https://$API_HOST/$API_VERSION/"
	}

	private val retrofit: Retrofit by lazy {
		retrofitBuilder().build()
	}

	protected open fun retrofitBuilder(): Retrofit.Builder {
		val gson = gsonBuilder().create()

		return Retrofit.Builder()
				.baseUrl(API_URL)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(okHttpClientBuilder().build())
	}

	protected open fun okHttpClientBuilder(): OkHttpClient.Builder {
		return OkHttpClient.Builder()
				.addInterceptor(TMDBAPIKeyInterceptor(apiKey))
	}

	protected open fun gsonBuilder(): GsonBuilder {
		return GsonBuilder().setDateFormat("yyyy-MM-dd")
	}

	fun movies(): Movies {
		return retrofit.create(Movies::class.java)
	}

	fun genres(): Genres {
		return retrofit.create(Genres::class.java)
	}

	fun configuration(): Configuration {
		return retrofit.create(Configuration::class.java)
	}

	fun search(): Search {
		return retrofit.create(Search::class.java)
	}

}