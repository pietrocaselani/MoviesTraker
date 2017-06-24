package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.GlidePalette
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.moviedetails.MovieDetailsViewModel

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("moviePoster")
fun bindMoviePoster(imageView: ImageView, viewModel: MovieDetailsViewModel) {
	val posterLink = viewModel.posterLink.get()

	val listener = GlidePalette.with(posterLink)
			.intoCallBack { palette ->
				if (palette != null) {
					val darkMutedColor = palette.getDarkMutedColor(R.color.colorPrimaryDark)
					val darkVibrantColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark)
					val vibrantColor = palette.getVibrantColor(R.color.colorAccent)
					val lightVibrantColor = palette.getMutedColor(android.R.color.white)

					viewModel.darkMutedColor.set(darkMutedColor)
					viewModel.darkVibrantColor.set(darkVibrantColor)
					viewModel.vibrantColor.set(vibrantColor)
					viewModel.lightVibrantColor.set(lightVibrantColor)
				}
			}

	Glide.with(imageView)
			.load(posterLink)
			.listener(listener)
			.into(imageView)
}