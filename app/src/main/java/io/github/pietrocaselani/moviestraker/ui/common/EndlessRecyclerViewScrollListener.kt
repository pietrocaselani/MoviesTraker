package io.github.pietrocaselani.moviestraker.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


/**
 * Created by pc on 24/06/17.
 */
class EndlessRecyclerViewScrollListener(
		private val layoutManager: LinearLayoutManager,
        private val callback: Callback
) : RecyclerView.OnScrollListener() {
	private var previousTotal = 0 // The total number of items in the dataset after the last load
	private var loading = true // True if we are still waiting for the last set of data to load.
	private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.
	private var firstVisibleItem: Int = 0
	private var visibleItemCount:Int = 0
	private var totalItemCount:Int = 0

	override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
		visibleItemCount = layoutManager.childCount;
		totalItemCount = layoutManager.itemCount;
		firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

		if (loading) {
			if (totalItemCount > previousTotal) {
				loading = false
				previousTotal = totalItemCount
			}
		}
		if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			callback.onLoadMore()

			loading = true
		}
	}

	interface Callback {
		fun onLoadMore()
	}

}