package com.swensun.swdesign.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.swensun.swdesign.database.TABLE_DOUBANMOVIE

/**
 * Created by macmini on 2017/8/16.
 */

@Entity(tableName = TABLE_DOUBANMOVIE, indices = arrayOf(Index(value = "movieId", unique = true)))
class DoubanMovieEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var title = ""

    var movieId: String = ""
    var image: String = ""
    var score: String = ""
}