package io.github.pietrocaselani.moviestraker.helpers

import android.os.Build
import android.widget.ImageView

/**
 * Created by pc on 24/06/17.
 */
fun ImageView.setCompatAlpha(alpha: Int) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
		imageAlpha = alpha
	} else {
		setAlpha(alpha)
	}
}