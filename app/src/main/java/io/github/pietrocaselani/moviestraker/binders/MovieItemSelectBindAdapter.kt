package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.pietrocaselani.moviestraker.upcoming.UpcomingViewModel

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("movieSelected")
fun movieSelectBind(recyclerView: RecyclerView, viewModel: UpcomingViewModel) {
	recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
		override fun onChildViewDetachedFromWindow(view: View?) {}

		override fun onChildViewAttachedToWindow(view: View?) {
			view?.setOnClickListener {
				val position = recyclerView.getChildAdapterPosition(it)
				viewModel.selectMovieAtIndex(position)
			}
		}

	})
}