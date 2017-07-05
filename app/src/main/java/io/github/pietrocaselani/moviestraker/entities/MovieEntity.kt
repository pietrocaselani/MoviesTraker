package io.github.pietrocaselani.moviestraker.entities

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel
import java.util.Date

/**
 * Created by pc on 24/06/17.
 */
@PaperParcel
data class MovieEntity(
		val id: Int,
		val title: String,
		val overview: String,
		val posterLink: String?,
		val backdropLink: String?,
		val releaseDate: Date,
		val genres: List<GenreEntity>
) : Parcelable {

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		PaperParcelMovieEntity.writeToParcel(this, parcel, flags)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object {
		@JvmField val CREATOR = PaperParcelMovieEntity.CREATOR
	}
}