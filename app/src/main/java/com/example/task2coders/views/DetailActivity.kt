package com.example.task2coders.views

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabaseLockedException
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.task2coders.R
import com.example.task2coders.services.SQLConection
import com.example.task2coders.services.SQLConection.FavTable.TABLE_NAME
import com.example.task2coders.models.Film


class DetailActivity : AppCompatActivity() {
    private lateinit var film: Film
    private lateinit var filmView: ImageView
    private lateinit var filmTitleView: TextView
    private lateinit var backBtn: ImageView
    private lateinit var favouriteBtn: ImageView
    private lateinit var shared: SharedPreferences
    var isAlreadyFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_details)

        val dbHelper = SQLConection(this)
        val db = dbHelper.writableDatabase

        film = intent.getParcelableExtra<Film>(MainActivity.INTENT_PARCELABLE)
        initViewContent()
        backBtn.setOnClickListener{ goMainAcitvity() }

        val projection = arrayOf("name", "is_fav")
        val selection = "name = ?"
        val selectionArgs = arrayOf(film.title)
        val cursor: Cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        isAlreadyFav = dbHelper.isAlreadyFav(cursor)
        changeFavImg()

        favouriteBtn.setOnClickListener{
            val cursor: Cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            with(cursor) {
                if (cursor.count <= 0) {
                    db?.insert(TABLE_NAME, null, ContentValues().apply { put("is_fav", true); put("name", film.title) })
                    favouriteBtn.setImageResource(R.drawable.heart)
                } else {
                    updateFav(db, selection)
                    changeFavImg()
                }
            }
        }

    }

    fun initViewContent() {
        filmView = findViewById<ImageView>(R.id.imageView3)
        filmTitleView = findViewById<TextView>(R.id.textView)
        backBtn = findViewById<ImageView>(R.id.imageView4)
        favouriteBtn = findViewById<ImageView>(R.id.imageView5)
        Glide.with(this).asBitmap().load("https://image.tmdb.org/t/p/original${film.backdrop_path}").into(filmView)
        filmTitleView.text = film.title.toUpperCase()
    }

    fun goMainAcitvity() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }

    fun updateFav(db: SQLiteDatabase, selection: String) {
        isAlreadyFav = !isAlreadyFav;
        db.update(TABLE_NAME, ContentValues().apply { put("is_fav", isAlreadyFav) }, "name = ?", arrayOf(film.title))
    }

    fun changeFavImg() {
        if (isAlreadyFav) favouriteBtn.setImageResource(R.drawable.heart)
        else favouriteBtn.setImageResource(R.drawable.heart_void)
    }
}
