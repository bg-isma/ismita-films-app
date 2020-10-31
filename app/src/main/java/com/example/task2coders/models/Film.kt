package com.example.task2coders.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    val url: String?,
    val popularity: Double,
    val vote_count : Int,
    val video : Boolean,
    val poster_path : String,
    val id : String,
    val adult : Boolean,
    val backdrop_path : String,
    val original_language : String,
    val original_title : String,
    val genre_ids : ArrayList<Int>,
    val title: String,
    val vote_average: Float,
    val overview: String,
    val release_date: String) : Parcelable {}