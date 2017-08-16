package com.swensun.swdesign.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by macmini on 2017/8/16.
 */
@Entity(tableName = "movie_data", indices = arrayOf(Index(value = "movieId", unique = true)), foreignKeys = arrayOf(ForeignKey(entity = DoubanMovieEntity::class,
        parentColumns = arrayOf("movieId"), childColumns = arrayOf("movieId"),
        onDelete = CASCADE, onUpdate = CASCADE)))
class MovieDataEntity {
    @PrimaryKey
    var movieId = 0
    var type = MovieDataType.TYPE.ordinal
    var typeData = ""
    var name = ""
    var logo = ""

}



