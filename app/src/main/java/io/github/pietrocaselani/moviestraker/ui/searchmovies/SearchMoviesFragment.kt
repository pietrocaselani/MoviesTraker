package io.github.pietrocaselani.moviestraker.ui.searchmovies

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.databinding.FragmentSearchMoviesBinding
import io.github.pietrocaselani.moviestraker.di.Injectable
import io.github.pietrocaselani.moviestraker.ui.NavigationController
import io.github.pietrocaselani.moviestraker.ui.common.EndlessRecyclerViewScrollListener
import javax.inject.Inject

/**
 * Created by pc on 25/06/17.
 */
class SearchMoviesFragment : Fragment(), Injectable {

	companion object {
		val TAG = "SearchMoviesFragment"
	}

	@Inject lateinit var navigationController: NavigationController
	@Inject lateinit var viewModel: SearchMoviesViewModel

	private lateinit var binding: FragmentSearchMoviesBinding
	private lateinit var endlessListener: EndlessRecyclerViewScrollListener

	private val movieChangedCallback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
			val movie = viewModel.selectedMovie.get()
			navigationController.navigateToMovieDetails(movie)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate<FragmentSearchMoviesBinding>(
				inflater,
				R.layout.fragment_search_movies,
				container,
				false)

		binding.viewModel = viewModel

		val linearLayoutManager = LinearLayoutManager(binding.root.context)
		binding.recyclerviewMovies.layoutManager = linearLayoutManager

		val callback = object : EndlessRecyclerViewScrollListener.Callback {
			override fun onLoadMore() {
				viewModel.requestMoreMovies()
			}

		}

		endlessListener = EndlessRecyclerViewScrollListener(linearLayoutManager, callback)
		binding.recyclerviewMovies.addOnScrollListener(endlessListener)
		endlessListener.loading = false

		return binding.root
	}

	override fun onStart() {
		super.onStart()

		viewModel.onStart()

		viewModel.selectedMovie.addOnPropertyChangedCallback(movieChangedCallback)
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.search_movies_fragment, menu)

		val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.searchview)) as SearchView

		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				viewModel.search(query ?: "")
				searchView.clearFocus()
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return true
			}
		})

		searchView.setIconifiedByDefault(false)
	}

	override fun onStop() {
		super.onStop()

		viewModel.selectedMovie.removeOnPropertyChangedCallback(movieChangedCallback)

		viewModel.onStop()
	}

	override fun onDestroy() {
		binding.recyclerviewMovies.clearOnScrollListeners()
		super.onDestroy()
	}

}