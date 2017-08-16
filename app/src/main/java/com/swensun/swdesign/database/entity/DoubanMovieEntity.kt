package com.swensun.swdesign.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.swensun.swdesign.database.TABLE_DOUBANMOVIE

/**
 * Created by macmini on 2017/8/16.
 */

@Entity(tableName = TABLE_DOUBANMOVIE)
class DoubanMovieEntity {
    var title = ""
    @PrimaryKey()
    var movieId: String = ""
    var image: String = ""
    var score: String = ""
}