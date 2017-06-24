package io.github.pietrocaselani.moviestraker.tmdb

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.github.pietrocaselani.moviestraker.tmdb.services.Movies
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

}