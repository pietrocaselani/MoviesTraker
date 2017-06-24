package io.github.pietrocaselani.moviestraker.upcoming

import android.databinding.BaseObservable
import android.databinding.ObservableField
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import okhttp3.HttpUrl
import java.text.SimpleDateFormat

/**
 * Created by pc on 24/06/17.
 */
class UpcomingViewModel(private val interactor: UpcomingInteractorInput) : BaseObservable() {

	//region Data Binding Properties
	val movies = ObservableField<MutableList<MovieListViewModel>>(mutableListOf())
	val message = ObservableField<String>("")
	val moviesVisibility = ObservableField<Boolean>(true)
	val messageVisibility = ObservableField<Boolean>(true)
	//endregion

	//region Properties
	private val disposables = CompositeDisposable()

	private var imageConfiguration: ImageConfigurationResponse? = null
	private val movieEntities = mutableListOf<MovieResponse>()
	private var genres = listOf<GenreResponse>()
	private var currentPage = 1
	private var totalPages = Int.MAX_VALUE
	//endregion

	//region Lifecycle
	fun onStart() {
		startRequests()
	}

	fun onStop() {
		disposables.clear()
	}
	//endregion

	//region Public
	fun requestMoreMovies() {
		currentPage++
		loadMovies()
	}
	//endregion

	//region Private
	private fun startRequests() {
		fetchGenres()
	}

	private fun loadMovies() {
		showMessage("Loading movies...")

		if (currentPage >= totalPages) {
			return
		}

		interactor.fetchUpcomingMovies(currentPage)
				.doOnNext {
					totalPages = it.totalPages
					movieEntities.addAll(it.movies)
				}
				.map {
					mapToMovieListViewModel(it.movies)
				}
				.subscribe({
					showMovies(it)
				}, {
					showMessage(it.localizedMessage)
				})
	}

	private fun fetchGenres() {
		if (genres.isEmpty()) {
			val disposable = interactor.fetchGenres()
					.map { it.genres }
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({
						genres = it
					}, {
						showMessage(it.localizedMessage)
					}, {
						fetchConfigurations()
					})
			disposables.add(disposable)
		} else {
			fetchConfigurations()
		}
	}

	private fun fetchConfigurations() {
		if (imageConfiguration == null) {
			val disposable = interactor.fetchImageConfigurations()
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({
						imageConfiguration = it
					}, {
						showMessage(it.localizedMessage)
					}, {
						refresh()
					})
			disposables.add(disposable)
		} else {
			refresh()
		}
	}

	private fun refresh() {
		currentPage = 1
		loadMovies()
	}

	private fun mapToMovieListViewModel(moviesResponse: List<MovieResponse>): List<MovieListViewModel> {
		val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)

		return moviesResponse.map { movieResponse ->
			val movieGenres = genres.filter {
				movieResponse.genreIds.contains(it.id)
			}.map {
				it.name
			}

			var movieImageLink: String? = null

			if (movieResponse.posterPath != null) {
				movieImageLink = buildPosterURL(movieResponse.posterPath)
			} else if (movieResponse.backdropPath != null) {
				movieImageLink = buildBackdropURL(movieResponse.backdropPath)
			}

			MovieListViewModel(
					name = movieResponse.title,
					imageLink = movieImageLink,
					genres = movieGenres,
					releaseDate = dateFormat.format(movieResponse.releaseDate)
			)
		}
	}

	private fun buildPosterURL(posterPath: String): String? {
		val sizePath = imageConfiguration?.posterSizes?.firstOrNull()

		val path = imageConfiguration?.secureBaseURL?.plus(sizePath)?.plus(posterPath)

		val url = HttpUrl.parse(path)

		return url?.toString()
	}

	private fun buildBackdropURL(backdropPath: String): String? {
		val sizePath = imageConfiguration?.backdropSizes?.firstOrNull()

		val path = imageConfiguration?.secureBaseURL?.plus(sizePath)?.plus(backdropPath)

		val url = HttpUrl.parse(path)

		return url?.toString()
	}
	//endregion

	//region View
	private fun showMessage(text: String) {
		message.set(text)
		moviesVisibility.set(false)
		messageVisibility.set(true)
	}

	private fun showMovies(moviesViewModel: List<MovieListViewModel>) {
//		val currentMovies = mutableListOf<MovieListViewModel>()
//		currentMovies.addAll(movies.get())
//		currentMovies.addAll(moviesViewModel)

		movies.set(moviesViewModel.toMutableList())

		if (moviesVisibility.get() != true) {
			messageVisibility.set(false)
			moviesVisibility.set(true)
		}
	}
	//endregion

}