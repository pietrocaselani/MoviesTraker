package io.github.pietrocaselani.moviestraker.upcoming

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.databinding.FragmentUpcomingBinding
import io.github.pietrocaselani.moviestraker.di.Injectable
import javax.inject.Inject

/**
 * Created by pc on 24/06/17.
 */
class UpcomingFragment : Fragment(), Injectable {

	companion object {
		const val TAG = "UpcomingFragment"
	}

	@Inject lateinit var viewModel: UpcomingViewModel
	private lateinit var binding: FragmentUpcomingBinding

	private lateinit var endlessListener: EndlessRecyclerViewScrollListener

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate<FragmentUpcomingBinding>(
				inflater,
				R.layout.fragment_upcoming,
				container,
				false)

		binding.viewModel = viewModel

		val linearLayoutManager = LinearLayoutManager(binding.root.context)
		binding.recyclerviewMovies.layoutManager = linearLayoutManager

		endlessListener = EndlessRecyclerViewScrollListener(linearLayoutManager)
		binding.recyclerviewMovies.addOnScrollListener(endlessListener)

		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		endlessListener.asObservable().subscribe {
			viewModel.requestMoreMovies()
		}

		viewModel.onStart()
	}

	override fun onStop() {
		super.onStop()

		viewModel.onStop()
	}

	override fun onDestroy() {
		binding.recyclerviewMovies.clearOnScrollListeners()
		super.onDestroy()
	}
}