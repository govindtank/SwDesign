package com.swensun.swdesign


import android.content.Intent
import android.os.Bundle
import com.swensun.swdesign.base.SwAppCompatActivity
import com.swensun.swdesign.ui.MainActivity
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit


class SplashActivity : SwAppCompatActivity() {

    companion object {
        val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Flowable.timer(500, TimeUnit.MICROSECONDS)
                .subscribe {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
    }
}
