package com.swensun.swdesign.ui.ins

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.swensun.swdesign.R
import com.swensun.swdesign.base.isAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import kotlinx.android.synthetic.main.activity_instagram_pro.*
import kotlinx.android.synthetic.main.content_instagram_pro.*

var isAutoLike = false
class InstagramProActivity : AppCompatActivity() {
    
    companion object {
        val TAG = "InstagramProActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instagram_pro)
        setSupportActionBar(toolbar)

        initView()

    }


    override fun onResume() {
        super.onResume()
        cip_switch_open_ins.isChecked = isAutoLike
    }

    private fun initView() {

        cip_switch_open_ins.setOnCheckedChangeListener { _, isChecked ->
            isAutoLike = isChecked
        }
        cip_btn_open_ins.setOnClickListener {
            Log.d(TAG, isAutoLike.toString())
            if (isAccessibilityServiceEnable()) {
                val intent = packageManager.getLaunchIntentForPackage("com.instagram.android")
                intent?.let {
                    startActivity(intent)
                }
            } else {
                openAccessibilitySetting(this)
            }
        }
    }

}
