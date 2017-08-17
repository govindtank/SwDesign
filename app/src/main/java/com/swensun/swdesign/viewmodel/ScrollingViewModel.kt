package com.swensun.swdesign.viewmodel

import android.arch.lifecycle.ViewModel
import com.swensun.swdesign.dagger2.main.DaggerScrollingComponent
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.repository.DoubanMovieRepository
import javax.inject.Inject

/**
 * Created by macmini on 2017/8/17.
 */

class ScrollingViewModel : ViewModel() {
    @Inject
    lateinit  var doubanMovieRepository: DoubanMovieRepository

    init {
        DaggerScrollingComponent.builder()
                .build().inject(this)
    }

    fun getDoubanMovieEntity(): DoubanMovieEntity {
        return doubanMovieRepository.doubanMovieEntity
    }
}
