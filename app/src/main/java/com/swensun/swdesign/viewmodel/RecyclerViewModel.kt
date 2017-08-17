package com.swensun.swdesign.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.swensun.swdesign.dagger2.main.DaggerRecyclerComponent
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.repository.DoubanMovieRepository
import javax.inject.Inject

/**
 * Created by macmini on 2017/8/16.
 */
class RecyclerViewModel: ViewModel() {

    @Inject
    lateinit var doubanMovieRepository: DoubanMovieRepository

    var moviesLiveData = MutableLiveData<List<DoubanMovieEntity>>()
    init {
        DaggerRecyclerComponent.builder().build().inject(this)
    }

    fun queryAllMovies() {
        doubanMovieRepository.queryAllMovies(moviesLiveData)
    }

    fun setDoubanMovieEntity(entity: DoubanMovieEntity) {
        doubanMovieRepository.doubanMovieEntity = entity
    }

}