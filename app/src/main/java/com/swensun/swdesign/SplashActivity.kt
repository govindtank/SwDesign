package com.swensun.swdesign


import android.os.Bundle
import com.swensun.swdesign.base.SwAppCompatActivity


class SplashActivity : SwAppCompatActivity() {

    companion object {
        val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        Flowable.timer(3, TimeUnit.SECONDS)
//                .subscribe {
//                    startActivity()
//                }
    }
}

