package io.github.pietrocaselani.moviestraker.upcoming

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by pc on 24/06/17.
 */
class EndlessRecyclerViewScrollListener(
		private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {
	private var visibleThreshold = 5
	private var currentPage = 0
	private var previousTotalItemCount = 0
	private var loading = true

	private val startingPageIndex = 0

	private val subject = PublishSubject.create<Int>()

	fun asObservable(): Observable<Int> = subject

	fun resetState() {
		this.currentPage = this.startingPageIndex
		this.previousTotalItemCount = 0
		this.loading = true
	}

	override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
		val totalItemCount = layoutManager.itemCount

		val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

		if (totalItemCount < previousTotalItemCount) {
			this.currentPage = this.startingPageIndex
			this.previousTotalItemCount = totalItemCount
			if (totalItemCount == 0) {
				this.loading = true
			}
		}

		if (loading && totalItemCount > previousTotalItemCount) {
			loading = false
			previousTotalItemCount = totalItemCount
		}


		if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
			currentPage++
			subject.onNext(currentPage)
			loading = true
		}
	}

}