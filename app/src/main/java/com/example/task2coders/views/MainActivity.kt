package com.example.task2coders.views

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2coders.BuildConfig
import com.example.task2coders.models.FilmResponse
import com.example.task2coders.R
import com.example.task2coders.models.Film
import com.example.task2coders.Adapters.FilmListAdapter
import com.example.task2coders.models.Genre
import com.example.task2coders.models.GenreResponse
import com.example.task2coders.services.ApiService
import com.example.task2coders.services.FilmService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filmService = ApiService.init();
        getTopFilms(filmService)
        getFilmsGenre(filmService)

    }

    fun loadList(films: List<Film>, list: RecyclerView) {
        list.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        list.adapter = FilmListAdapter(this@MainActivity, films as ArrayList<Film>) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }

    private fun getTopFilms(filmService: FilmService) {
        filmService.getTopFilms(BuildConfig.imdbApikey).enqueue(object : Callback<FilmResponse> {
            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {}
            override fun onResponse(call: Call<FilmResponse>, response: Response<FilmResponse>) {
                loadList(response.body()?.results ?: arrayListOf(), top_films_list)
            }
        })
    }

    private fun getAllMovies(filmService: FilmService, genres: List<Genre>) {
        filmService.getAllFilms(BuildConfig.imdbApikey, 2).enqueue(object : Callback<FilmResponse> {
            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {}
            override fun onResponse(call: Call<FilmResponse>, response: Response<FilmResponse>) {
                loadList(response.body()?.results?.filter{ it.genre_ids.indexOf(getGenreID(genres, "Romance")) !== -1 } ?: arrayListOf(), romance_films_list)
                loadList(response.body()?.results?.filter{ it.genre_ids.indexOf(getGenreID(genres, "Science Fiction")) !== -1 } ?: arrayListOf(), science_fiction_films_list)
            }
        })
    }

    private fun getGenreID(genres: List<Genre>, genreType: String): Int? {
        return genres.find { it.name.equals(genreType) }?.id
    }

    private fun getFilmsGenre(filmService: FilmService) {
        filmService.getAllFilmsGenre(BuildConfig.imdbApikey).enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {}
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                response.body()?.genres?.let { getAllMovies(filmService, it) }
            }
        })
    }
}
