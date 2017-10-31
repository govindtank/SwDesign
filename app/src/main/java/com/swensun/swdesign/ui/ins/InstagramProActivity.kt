package com.swensun.swdesign.ui.ins

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.swensun.swdesign.R
import com.swensun.swdesign.base.isAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import kotlinx.android.synthetic.main.activity_instagram_pro.*
import kotlinx.android.synthetic.main.content_instagram_pro.*


var isAttention = true

object InsSetting {
    var isAutoLike = false
    var action = InsAction.ATTENTION
    var page = 10
    var interval = 10
}

enum class InsAction {
    LIKE, ATTENTION, NOTATTENTION
}

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
        InsSetting.isAutoLike = false
    }

    private fun initView() {
        initSpinner()
        cip_btn_open_ins.setOnClickListener {
            InsSetting.isAutoLike = true
            if (isAccessibilityServiceEnable()) {
                val intent = packageManager.getLaunchIntentForPackage("com.instagram.android")
                intent?.let {
                    startActivity(intent)
                }
            } else {
                openAccessibilitySetting(this)
            }
//            Log.d(TAG, InsSetting.action.toString())
//            Log.d(TAG, InsSetting.page.toString())
//            Log.d(TAG, InsSetting.interval.toString())
        }
    }

    private fun initSpinner() {

        var pageList = arrayListOf(1, 2, 5)
        val pageAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1, pageList)
        cip_spinner_page.prompt = "请选择页数"
        cip_spinner_page.adapter = pageAdapter
        cip_spinner_page.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                InsSetting.page = pageList[position]
            }
        }

        var actionList = arrayListOf("关注", "取关", "点赞")
        val actionAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionList)
        cip_spinner_action.prompt = "请选择行为"
        cip_spinner_action.adapter = actionAdapter
        cip_spinner_action.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        InsSetting.action = InsAction.ATTENTION
                        cip_spinner_page.setSelection(0)
                        InsSetting.interval = 10
                        cip_btn_open_ins.text = "开始关注"
                    }
                    1 -> {
                        InsSetting.action = InsAction.NOTATTENTION
                        cip_spinner_page.setSelection(0)
                        InsSetting.interval = 10
                        cip_btn_open_ins.text = "开始取关"
                    }
                    2 -> {
                        InsSetting.action = InsAction.LIKE
                        cip_spinner_page.setSelection(2)
                        InsSetting.interval = 1
                        cip_btn_open_ins.text = "开始点赞"
                    }
                }
            }
        }
    }
}
