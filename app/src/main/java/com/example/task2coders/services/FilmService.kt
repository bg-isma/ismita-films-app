package com.example.task2coders.services

import com.example.task2coders.models.FilmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("3/discover/movie")
    fun getAllFilms( @Query("api_key") apiKey: String): Call<FilmResponse>

}