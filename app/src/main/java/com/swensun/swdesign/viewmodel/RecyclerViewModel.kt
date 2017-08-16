package com.swensun.swdesign.viewmodel

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.swensun.swdesign.App.BaseApplication
import com.swensun.swdesign.R
import com.swensun.swdesign.dagger2.main.DaggerRecyclerComponent
import com.swensun.swdesign.data.DoubanMovie
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.repository.DoubanMovieRepository
import javax.inject.Inject

/**
 * Created by macmini on 2017/8/16.
 */
class RecyclerViewModel: ViewModel() {

    @Inject
    lateinit var doubanMovieRepository: DoubanMovieRepository

    init {
        DaggerRecyclerComponent.builder().build().inject(this)
    }


}