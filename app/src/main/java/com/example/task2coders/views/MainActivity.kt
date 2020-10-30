package com.example.task2coders.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.task2coders.models.FilmResponse
import com.example.task2coders.R
import com.example.task2coders.models.Film
import com.example.task2coders.controllers.FilmList
import com.example.task2coders.services.FilmService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/").addConverterFactory(GsonConverterFactory.create()).build();
        val filmService: FilmService = retrofit.create<FilmService>(FilmService::class.java)
        val result: Call<FilmResponse> = filmService.getAllFilms("0da2d6744b7dda175fe4aed86c1bf257");
        result.enqueue(object : Callback<FilmResponse> {
            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<FilmResponse>, response: Response<FilmResponse>) {
                loadList(response.body()?.results ?: arrayListOf())
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun loadList(films: ArrayList<Film>) {
        filmList.layoutManager = LinearLayoutManager(this@MainActivity, OrientationHelper.HORIZONTAL, false)
        filmList.adapter = FilmList(this@MainActivity, films) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }
}
