package com.swensun.swdesign.dagger2.main

import com.swensun.swdesign.dagger2.RepositoriesModule
import com.swensun.swdesign.viewmodel.ScrollingViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by macmini on 2017/8/16.
 */
@Singleton
@Component(modules = arrayOf(RepositoriesModule::class))
interface ScrollingComponent {

    fun inject(scrollingViewModel: ScrollingViewModel)
}