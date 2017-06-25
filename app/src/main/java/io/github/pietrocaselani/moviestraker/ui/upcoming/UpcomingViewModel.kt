package io.github.pietrocaselani.moviestraker.ui.upcoming

import android.databinding.BaseObservable
import android.databinding.ObservableField
import io.github.pietrocaselani.moviestraker.entities.GenreEntity
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
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
	val selectedMovie = ObservableField<MovieEntity>()
	//endregion

	//region Properties
	private val disposables = CompositeDisposable()

	private var imageConfiguration: ImageConfigurationResponse? = null
	private val movieEntities = mutableListOf<MovieEntity>()
	private var genres = listOf<GenreResponse>()
	private val loadedPages = mutableListOf<Int>()
	private var currentPage = 0
	private var totalPages = Int.MAX_VALUE
	private var loading = false
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
	fun isLoading(): Boolean {
		return loading
	}

	fun hasLoadedAllPages(): Boolean {
		return currentPage == totalPages
	}

	fun requestMoreMovies() {
		loading = true
		currentPage++
		loadMovies()
	}

	fun selectMovieAtIndex(index: Int) {
		val movie = movieEntities[index]

		if (selectedMovie.get() != null && movie.id == selectedMovie.get().id) {
			selectedMovie.notifyChange()
		} else {
			val basePath = imageConfiguration?.let {
				var sizePath = it.posterSizes.find {
					it.contains("w3")
				}

				sizePath = sizePath ?: it.posterSizes.last()

				it.secureBaseURL + sizePath
			}

			var biggerPosterLink: String? = null
			var biggerBackdropLink: String? = null

			if (movie.posterLink != null) {
				val posterLink = HttpUrl.parse(movie.posterLink)?.pathSegments()?.last()
				biggerPosterLink = HttpUrl.parse(basePath)?.newBuilder()
						?.addPathSegment(posterLink)
						?.build()
						?.toString()
			} else if (movie.backdropLink != null) {
				val backdropLink = HttpUrl.parse(movie.backdropLink)?.pathSegments()?.last()
				biggerBackdropLink = HttpUrl.parse(basePath)?.newBuilder()
						?.addPathSegment(backdropLink)
						?.build()
						?.toString()
			}

			val movieToShow = MovieEntity(
					id = movie.id,
					title = movie.title,
					overview = movie.overview,
					releaseDate = movie.releaseDate,
					genres = movie.genres,
					posterLink = biggerPosterLink,
					backdropLink = biggerBackdropLink
			)

			selectedMovie.set(movieToShow)
		}
	}
	//endregion

	//region Private
	private fun startRequests() {
		if (movieEntities.size == 0) {
			showMessage("Loading movies...")
		}

		fetchGenres()
	}

	private fun loadMovies() {
		if (currentPage >= totalPages || loadedPages.contains(currentPage)) {
			return
		}

		loading = true

		interactor.fetchUpcomingMovies(currentPage)
				.doOnNext {
					loadedPages.add(it.page)
					totalPages = it.totalPages
				}
				.map {
					mapToMovieEntity(it.movies)
				}
				.doOnNext {
					movieEntities.addAll(it)
				}
				.map {
					mapToMovieListViewModel(it)
				}
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					showMovies(it)
				}, {
					loading = false
					showMessage(it.localizedMessage)
				}, {
					loading = false
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

	private fun mapToMovieEntity(movies: List<MovieResponse>): List<MovieEntity> {
		return movies.map { movie ->
			val movieGenres = genres.filter {
				movie.genreIds.contains(it.id)
			}.map {
				GenreEntity(it.id, it.name)
			}

			val basePath = imageConfiguration?.let {
				val sizePath = it.posterSizes.first()
				it.secureBaseURL + sizePath
			}

			val posterLink = basePath?.plus(movie.posterPath)
			val backdropLink = basePath?.plus(movie.backdropPath)

			MovieEntity(
					id = movie.id,
					title = movie.title,
					overview = movie.overview,
					posterLink = posterLink,
					backdropLink = backdropLink,
					releaseDate = movie.releaseDate,
					genres = movieGenres
			)
		}
	}

	private fun mapToMovieListViewModel(moviesEntity: List<MovieEntity>): List<MovieListViewModel> {
		val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)

		return moviesEntity.map { movieEntity ->
			val movieGenres = movieEntity.genres.map {
				it.name
			}

			val imageLink: String?

			if (movieEntity.posterLink != null) {
				imageLink = movieEntity.posterLink
			} else if (movieEntity.backdropLink != null) {
				imageLink = movieEntity.backdropLink
			} else {
				imageLink = null
			}

			MovieListViewModel(
					name = movieEntity.title,
					imageLink = imageLink,
					genres = movieGenres,
					releaseDate = dateFormat.format(movieEntity.releaseDate)
			)
		}
	}
	//endregion

	//region View
	private fun showMessage(text: String) {
		message.set(text)
		moviesVisibility.set(false)
		messageVisibility.set(true)
	}

	private fun showMovies(moviesViewModel: List<MovieListViewModel>) {
		val moviesList = movies.get()
		moviesList.addAll(moviesViewModel)

		movies.set(moviesList)
		movies.notifyChange()

		if (moviesVisibility.get() != true) {
			messageVisibility.set(false)
			moviesVisibility.set(true)
		}
	}
	//endregion

}