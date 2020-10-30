package com.example.task2coders.models

import com.example.task2coders.models.Film

data class FilmResponse (val page: Int, val total_results: Int, val total_pages: Int, var results: ArrayList<Film>) {
}