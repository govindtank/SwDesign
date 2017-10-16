package com.swensun.swdesign.database

import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.swensun.swdesign.app.BaseApplication
import com.swensun.swdesign.R
import com.swensun.swdesign.data.DoubanMovie
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.database.entity.MovieDataEntity
import com.swensun.swdesign.database.entity.MovieDataType

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
        var gson = Gson()
        var movies = arrayListOf<DoubanMovieEntity>()
        var movieDatas = arrayListOf<MovieDataEntity>()
        val movieTop250 = BaseApplication.application.resources.openRawResource(R.raw.doubanmovie)
                .bufferedReader().use { it.readText() }
        val doubanMovie = gson.fromJson(movieTop250, DoubanMovie::class.java)
        doubanMovie.subjects.forEach {
            val doubanmovieEntity = DoubanMovieEntity()
            doubanmovieEntity.title = it.title
            if (!it.original_title.isNullOrBlank()) {
                doubanmovieEntity.oriTitle = it.original_title
            }
            doubanmovieEntity.movieId = it.id
            doubanmovieEntity.score = it.rating.average.toString()
            doubanmovieEntity.image = it.images.medium
            movies.add(doubanmovieEntity)

            it.genres.forEach {
                val movieData = MovieDataEntity().apply { type = MovieDataType.GENRE.ordinal }
                movieData.movieId = doubanmovieEntity.movieId
                movieData.typeData = it
                movieDatas.add(movieData)

            }

            it.directors.forEach {
                val movieData = MovieDataEntity().apply { type = MovieDataType.DIRECTORS.ordinal }
                movieData.movieId = doubanmovieEntity.movieId
                movieData.name = it.name
                movieData.url = it.alt ?: ""
                movieData.logo = it.avatars.small ?: ""
                movieDatas.add(movieData)
            }
            it.casts.forEach {
                val movieData = MovieDataEntity().apply { type = MovieDataType.CAST.ordinal }
                movieData.movieId = doubanmovieEntity.movieId
                movieData.name = it.name
                movieData.url = it.alt ?: ""
                movieData.logo = if (it.avatars == null) "" else (it.avatars.small ?: "")
                movieDatas.add(movieData)
            }
        }
        db.doubanMovieDao().saveMovies(movies)
        val i = db.movieDataDao().saveMovieDatas(movieDatas)
    }


    fun deleteDoubanMovie(movieId : String) {
        db.doubanMovieDao().deleteMovie(movieId)
    }
}