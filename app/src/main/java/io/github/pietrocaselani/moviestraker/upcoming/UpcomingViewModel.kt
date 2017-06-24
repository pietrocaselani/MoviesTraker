package io.github.pietrocaselani.moviestraker.upcoming

import android.databinding.BaseObservable
import android.databinding.ObservableField
import io.github.pietrocaselani.moviestraker.entities.GenreEntity
import io.github.pietrocaselani.moviestraker.entities.MovieEntity
import io.github.pietrocaselani.moviestraker.tmdb.entities.GenreResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.ImageConfigurationResponse
import io.github.pietrocaselani.moviestraker.tmdb.entities.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

	fun selectMovieAtIndex(index: Int) {
		val movie = movieEntities[index]

		if (movie == selectedMovie.get()) {
			selectedMovie.notifyChange()
		} else {
			selectedMovie.set(movie)
		}
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
		movies.set(moviesViewModel.toMutableList())

		if (moviesVisibility.get() != true) {
			messageVisibility.set(false)
			moviesVisibility.set(true)
		}
	}
	//endregion

}