package com.swensun.swdesign.dagger2

import com.swensun.swdesign.App.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by macmini on 2017/8/17.
 */
@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideDoubanMovieRepository() = BaseApplication.repository.doubanMovieRepository
}