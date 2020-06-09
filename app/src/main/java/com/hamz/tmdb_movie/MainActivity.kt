package com.hamz.tmdb_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hamz.tmdb_movie.ui.genre.GenreActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_genre.setOnClickListener {
            Log.e("Main", "debug clicked")
            startActivity(Intent(applicationContext, GenreActivity::class.java))
        }
    }
}