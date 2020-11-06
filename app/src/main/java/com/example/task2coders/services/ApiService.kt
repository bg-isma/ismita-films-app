package com.example.task2coders.services

import com.example.task2coders.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiService() {

    companion object {
        fun init(): FilmService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FilmService::class.java);
            return retrofit
        }
    }

}