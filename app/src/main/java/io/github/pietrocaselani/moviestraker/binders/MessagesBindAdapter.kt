package io.github.pietrocaselani.moviestraker.binders

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Created by pc on 26/06/17.
 */
@BindingAdapter("message")
fun bindMessage(textView: TextView, message: String) {
	if (message.isNotEmpty()) {
		textView.text = message
	}
}

@BindingAdapter("resourceMessage")
fun bindResourceMessage(textView: TextView, stringResId: Int) {
	if (stringResId > 0) {
		textView.setText(stringResId)
	}
}