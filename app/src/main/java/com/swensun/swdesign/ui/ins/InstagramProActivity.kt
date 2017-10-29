package com.swensun.swdesign.ui.ins

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.swensun.swdesign.R
import com.swensun.swdesign.base.isAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import kotlinx.android.synthetic.main.activity_instagram_pro.*
import kotlinx.android.synthetic.main.content_instagram_pro.*

var isAutoLike = false
var isAttention = true
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
        isAutoLike = false
        cip_switch_open_ins.isChecked = isAttention
    }

    private fun initView() {

        cip_switch_open_ins.setOnCheckedChangeListener { _, isChecked ->
            isAttention = isChecked
            cip_btn_open_ins.text = if (isAttention) "Ins关注" else "Ins取关"
        }

        cip_btn_open_ins.setOnClickListener {
            isAutoLike = true
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
