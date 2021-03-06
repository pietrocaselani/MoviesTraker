package io.github.pietrocaselani.moviestraker.ui.upcoming

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import io.github.pietrocaselani.moviestraker.ui.NavigationController
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.databinding.FragmentUpcomingBinding
import io.github.pietrocaselani.moviestraker.di.Injectable
import io.github.pietrocaselani.moviestraker.ui.common.EndlessRecyclerViewScrollListener
import javax.inject.Inject

/**
 * Created by pc on 24/06/17.
 */
class UpcomingFragment : Fragment(), Injectable {

	companion object {
		const val TAG = "UpcomingFragment"
	}

	@Inject lateinit var viewModel: UpcomingViewModel
	@Inject lateinit var navigationController: NavigationController

	private lateinit var binding: FragmentUpcomingBinding
	private lateinit var endlessListener: EndlessRecyclerViewScrollListener

	private val movieChangedCallback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
			val movie = viewModel.selectedMovie.get()
			navigationController.navigateToMovieDetails(movie)
		}
	}

	private val loadingChangedCallback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
			if (!viewModel.loading.get()) {
				binding.swiperefreshMovies.isRefreshing = false
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate<FragmentUpcomingBinding>(
				inflater,
				R.layout.fragment_upcoming,
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

		binding.swiperefreshMovies.setOnRefreshListener {
			viewModel.refresh()
		}

		return binding.root
	}

	override fun onStart() {
		super.onStart()

		viewModel.onStart()

		viewModel.selectedMovie.addOnPropertyChangedCallback(movieChangedCallback)
		viewModel.loading.addOnPropertyChangedCallback(loadingChangedCallback)
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.upcoming_fragment, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == R.id.fragment_upcoming_menu_search) {
			navigationController.navigateToSearchMovies()

			return true
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onStop() {
		super.onStop()

		viewModel.selectedMovie.removeOnPropertyChangedCallback(movieChangedCallback)
		viewModel.loading.removeOnPropertyChangedCallback(loadingChangedCallback)

		viewModel.onStop()
	}

	override fun onDestroy() {
		binding.recyclerviewMovies.clearOnScrollListeners()
		super.onDestroy()
	}
}