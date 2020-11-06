package com.example.task2coders.models


data class FilmResponse (
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    var results: List<Film>
) {}