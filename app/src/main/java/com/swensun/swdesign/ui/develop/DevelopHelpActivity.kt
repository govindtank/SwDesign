package com.swensun.swdesign.ui.develop

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import com.swensun.swdesign.R
import com.swensun.swdesign.base.isDevelopAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import kotlinx.android.synthetic.main.activity_develop_help.*
import kotlinx.android.synthetic.main.content_develop_help.*

class DevelopHelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_develop_help)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        develop.setOnClickListener {
            if (isDevelopAccessibilityServiceEnable()) {
                val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                startActivity(intent)
            } else {
                openAccessibilitySetting(this)
            }
        }
    }
}
