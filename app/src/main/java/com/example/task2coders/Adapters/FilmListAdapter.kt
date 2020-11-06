package com.example.task2coders.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2coders.R
import com.example.task2coders.models.Film

class FilmListAdapter (
    private val context: Context,
    private val film: ArrayList<Film>,
    val listener: (Film) -> Unit) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).asBitmap().load("https://image.tmdb.org/t/p/original${film[position].backdrop_path}").into(holder.filmImg)
        holder.filmImg.setOnClickListener{ listener(film[position]) }
    }

    override fun getItemCount(): Int = film.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmImg: ImageView = itemView.findViewById(R.id.films_list_img)
    }
}