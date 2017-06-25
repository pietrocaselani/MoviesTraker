package io.github.pietrocaselani.moviestraker.ui.upcoming

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


/**
 * Created by pc on 24/06/17.
 */
class EndlessRecyclerViewScrollListener(
		private val layoutManager: LinearLayoutManager,
        private val callback: Callback
) : RecyclerView.OnScrollListener() {
	private val loadingTriggerThreshold = 5

	override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
		val visibleItemCount = layoutManager.childCount
		val totalItemCount = layoutManager.itemCount

		val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

		if (totalItemCount - visibleItemCount <= firstVisibleItemPosition + loadingTriggerThreshold || totalItemCount == 0) {
			if (!callback.isLoading() && !callback.hasLoadedAllItems()) {
				callback.onLoadMore()
			}
		}
	}

	interface Callback {
		fun isLoading(): Boolean

		fun hasLoadedAllItems(): Boolean

		fun onLoadMore()
	}

}