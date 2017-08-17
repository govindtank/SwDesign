package com.swensun.swdesign.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.swensun.swdesign.database.TABLE_DOUBANMOVIE
import com.swensun.swdesign.database.entity.DoubanMovieEntity

/**
 * Created by macmini on 2017/8/16.
 */
@Dao
interface DoubanMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<DoubanMovieEntity>)

    @Query("delete from $TABLE_DOUBANMOVIE where movieId = :movieId ")
    fun deleteMovie(movieId: String)
}