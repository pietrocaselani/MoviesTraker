package io.github.pietrocaselani.moviestraker.entities

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

/**
 * Created by pc on 24/06/17.
 */
@PaperParcel
data class GenreEntity(
		val id: Int,
        val name: String
) : Parcelable {

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		PaperParcelGenreEntity.writeToParcel(this, parcel, flags)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object {
		@JvmField val CREATOR = PaperParcelGenreEntity.CREATOR
	}
}