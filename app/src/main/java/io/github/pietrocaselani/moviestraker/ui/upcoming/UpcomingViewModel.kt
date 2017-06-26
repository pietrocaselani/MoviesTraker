package io.github.pietrocaselani.moviestraker.ui.upcoming

import android.databinding.BaseObservable
import android.databinding.ObservableField
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import io.github.pietrocaselani.moviestraker.helpers.mapMovieToDetails
import io.github.pietrocaselani.moviestraker.helpers.mapToMovieEntity
import io.github.pietrocaselani.moviestraker.helpers.mapToMovieListViewModel
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.ui.common.MovieListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

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
			val movieToShow = mapMovieToDetails(movie, imageConfiguration)

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
					mapToMovieEntity(it.results, genres, imageConfiguration)
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