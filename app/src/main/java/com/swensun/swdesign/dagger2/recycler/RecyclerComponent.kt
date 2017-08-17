package com.swensun.swdesign.dagger2.main

import com.swensun.swdesign.viewmodel.RecyclerViewModel
import dagger.Component

/**
 * Created by macmini on 2017/8/16.
 */
@Component
interface RecyclerComponent {

    fun inject(recyclerViewModel: RecyclerViewModel)
}