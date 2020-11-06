package com.example.task2coders.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    val genre_ids: Array<Int>,
    val backdrop_path : String,
    val title: String) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        if (!genre_ids.contentEquals(other.genre_ids)) return false
        if (backdrop_path != other.backdrop_path) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = genre_ids.contentHashCode()
        result = 31 * result + backdrop_path.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }

}