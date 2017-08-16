package com.swensun.swdesign.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.swensun.swdesign.database.entity.DoubanMovieEntity

/**
 * Created by macmini on 2017/8/16.
 */
@Dao
interface DoubanMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<DoubanMovieEntity>)
}