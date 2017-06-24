package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.view.View

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("booleanVisibility")
fun bindBooleanVisibility(view: View, visible: Boolean) {
	view.visibility = if (visible) View.VISIBLE else View.GONE
}