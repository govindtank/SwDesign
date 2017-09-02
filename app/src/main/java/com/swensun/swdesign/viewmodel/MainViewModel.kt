package com.swensun.swdesign.viewmodel

import android.arch.lifecycle.ViewModel
import com.swensun.swdesign.dagger2.main.DaggerMainComponent
import com.swensun.swdesign.shareprefence.SharedPerfsManager
import javax.inject.Inject

/**
 * Created by macmini on 2017/8/16.
 */

class MainViewModel : ViewModel() {



    @Inject lateinit var mSharedPerfsManager: SharedPerfsManager

    init {
        DaggerMainComponent.builder().build().inject(this)
    }


    fun shouldShowGuide(): Boolean {
        return mSharedPerfsManager["guide", true]
    }

    fun markGuide() {
        mSharedPerfsManager.put("guide", false)
    }



//    fun testFun() {
//        doAsync {
//            val movies = arrayListOf<DoubanMovieEntity>().apply {
//                (0..100).forEach {
//                    add(DoubanMovieEntity().apply {
//                        id = it
//                        title = (it * it).toString()
//                    })
//                }
//            }
//            doubanMovieRepository.saveMovies(movies)
//        }
//    }
}
