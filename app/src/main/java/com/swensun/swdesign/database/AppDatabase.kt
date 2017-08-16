package com.swensun.swdesign.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.swensun.swdesign.database.dao.DoubanMovieDao
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.database.entity.MovieDataEntity

/**
 * Created by macmini on 2017/8/16.
 */
@Database(version = DATABASE_VERSION, entities = arrayOf(DoubanMovieEntity::class,
        MovieDataEntity::class
        ))
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val TAG = "AppDatabase"
    }
    abstract fun doubanMovieDao(): DoubanMovieDao
}
