package com.swensun.swdesign.repository

import android.arch.lifecycle.MutableLiveData
import com.swensun.swdesign.database.DataBaseManager
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import org.jetbrains.anko.doAsync

/**
 * Created by macmini on 2017/8/16.
 */

class DoubanMovieRepository {

    val doubanMovieDao = DataBaseManager.db.doubanMovieDao()

    var doubanMovieEntity = DoubanMovieEntity()


    fun saveMovies(movies: List<DoubanMovieEntity>) {
        doubanMovieDao.saveMovies(movies)
    }

    fun queryAllMovies(moviesLiveData: MutableLiveData<List<DoubanMovieEntity>>) {
        doAsync {
            val movies = doubanMovieDao.queryAllMovies()
            moviesLiveData.postValue(movies)
        }

    }
}
