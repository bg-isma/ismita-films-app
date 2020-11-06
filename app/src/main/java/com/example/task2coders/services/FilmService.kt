package com.example.task2coders.services

import com.example.task2coders.models.FilmResponse
import com.example.task2coders.models.GenreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {

    @GET("3/movie/top_rated")
    fun getTopFilms( @Query("api_key") apiKey: String): Call<FilmResponse>

    @GET("3/movie/popular")
    fun getAllFilms( @Query("api_key") apiKey: String, @Query("page") page: Int): Call<FilmResponse>

    @GET("3/genre/movie/list")
    fun getAllFilmsGenre( @Query("api_key") apiKey: String): Call<GenreResponse>

}