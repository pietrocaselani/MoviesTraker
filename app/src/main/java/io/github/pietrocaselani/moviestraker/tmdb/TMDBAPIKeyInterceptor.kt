package io.github.pietrocaselani.moviestraker.tmdb

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/**
 * Created by pc on 24/06/17.
 */
internal class TMDBAPIKeyInterceptor(val apiKey: String) : Interceptor {

	override fun intercept(chain: Chain?): Response {
		val request = chain?.request()
		if (TMDB.API_HOST != request?.url()?.host()) {
			return chain?.proceed(request)!!
		}

		val builder = request.newBuilder()

		val apiKeyURL = request.url().newBuilder().setQueryParameter("api_key", apiKey).build()

		builder.url(apiKeyURL)

		return chain.proceed(builder.build())
	}

}