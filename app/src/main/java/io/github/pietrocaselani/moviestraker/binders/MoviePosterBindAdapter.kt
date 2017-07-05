package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.GlidePalette
import io.github.pietrocaselani.moviestraker.R
import io.github.pietrocaselani.moviestraker.helpers.setCompatAlpha
import io.github.pietrocaselani.moviestraker.ui.moviedetails.MovieDetailsViewModel

/**
 * Created by pc on 24/06/17.
 */
@BindingAdapter("moviePoster")
fun bindMoviePoster(imageView: ImageView, viewModel: MovieDetailsViewModel) {
	val posterLink = viewModel.posterLink.get()

	imageView.setCompatAlpha(30)

	val listener = GlidePalette.with(posterLink)
			.intoCallBack { palette ->
				if (palette != null) {
					val titleColor = palette.getDominantColor(R.color.colorPrimaryDark)
					val detailsColor = palette.getMutedColor(R.color.colorPrimaryDark)
					val overviewColor = palette.getLightVibrantColor(R.color.colorAccent)

					viewModel.titleColor.set(titleColor)
					viewModel.detailsColor.set(detailsColor)
					viewModel.overviewColor.set(overviewColor)
				}
			}

	Glide.with(imageView)
			.load(posterLink)
			.listener(listener)
			.into(imageView)
}