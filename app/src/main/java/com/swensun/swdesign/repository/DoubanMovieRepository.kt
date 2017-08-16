package com.swensun.swdesign.repository

import com.swensun.swdesign.database.DataBaseManager
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import javax.inject.Inject

/**
 * Created by macmini on 2017/8/16.
 */

class DoubanMovieRepository @Inject constructor() {

    val doubanMovieDao = DataBaseManager.db.doubanMovieDao()


    fun saveMovies(movies: List<DoubanMovieEntity>) {
        doubanMovieDao.saveMovies(movies)
    }
}
