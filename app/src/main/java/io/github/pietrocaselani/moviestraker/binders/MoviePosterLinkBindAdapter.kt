package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("moviePosterLink")
fun bindMoviePosterLink(imageView: ImageView, posterLink: String) {
	Glide.with(imageView.context)
			.load(posterLink)
			.into(imageView)
}