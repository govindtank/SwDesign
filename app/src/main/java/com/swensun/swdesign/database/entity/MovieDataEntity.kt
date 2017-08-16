package com.swensun.swdesign.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by macmini on 2017/8/16.
 */
@Entity(tableName = "movie_data")
class MovieDataEntity {
    @PrimaryKey
    var movieId = 0

}

enum class MovieDataType {
    TYPE, // 剧情， 动作之类
    CAST, // 演员
    DIRECTORS //导演
}


