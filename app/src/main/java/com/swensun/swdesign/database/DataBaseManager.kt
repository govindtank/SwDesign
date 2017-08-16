package com.swensun.swdesign.database

import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.swensun.swdesign.App.BaseApplication
import com.swensun.swdesign.R
import com.swensun.swdesign.data.DoubanMovie
import com.swensun.swdesign.database.entity.DoubanMovieEntity

/**
 * Created by macmini on 2017/8/16.
 */
object DataBaseManager {
    val builder = Room.databaseBuilder(BaseApplication.application, AppDatabase::class.java, DATABASE_NAME)!!
    var db: AppDatabase
    init {
        //migrations
//        builder.addMigrations()
        db = builder.build()
    }

    fun saveDoubanMovieEntnties(){
        var movies = arrayListOf<DoubanMovieEntity>()
        val movieTop250 = BaseApplication.application.resources.openRawResource(R.raw.doubanmovie)
                .bufferedReader().use { it.readText() }
        val doubanMovie = Gson().fromJson(movieTop250, DoubanMovie::class.java)
        doubanMovie.subjects.forEach {
            val doubanmovieEntity = DoubanMovieEntity()
            doubanmovieEntity.title = it.title
            doubanmovieEntity.movieId = it.id
            doubanmovieEntity.score = it.rating.average.toString()
            doubanmovieEntity.image = it.images.small
            movies.add(doubanmovieEntity)
            db.doubanMovieDao().saveMovies(movies)
        }
    }
}