package com.swensun.swdesign.ui.develop

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import com.swensun.swdesign.service.DevelopAction
import com.swensun.swdesign.service.DevelopData
import com.swensun.swdesign.service.DevelopSettings
import com.swensun.swdesign.ui.MainActivity
import com.swensun.swutils.util.*
import kotlinx.android.synthetic.main.activity_develop_help.*
import kotlinx.android.synthetic.main.content_develop_help.*


class DevelopHelpActivity : AppCompatActivity() {

    val adaper = RecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_develop_help)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        cdh_recycler_develop.layoutManager = LinearLayoutManager(this)
        cdh_recycler_develop.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        DevelopSettings.isAuto = false
        adaper.setItemList(actionList)
        cdh_recycler_develop.adapter = adaper
    }

    inner class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var mItemList = arrayListOf<DevelopData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.view_develop_settings_item, parent, false)
            return NormalItemViewHolder(itemView = view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as NormalItemViewHolder).updateView(mItemList[position])
        }

        override fun getItemCount(): Int {
            return mItemList.size
        }

        fun setItemList(datas: List<DevelopData>) {
            mItemList.clear()
            mItemList.addAll(datas)   //list的copy
            notifyDataSetChanged()
        }
    }

    inner class NormalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val infoView: TextView = itemView.findViewById(R.id.vdsi_tv_info)
        private val detailView: TextView = itemView.findViewById(R.id.vdsi_tv_detail)

        fun updateView(entity: DevelopData) {
            infoView.text = entity.info
            detailView.text = entity.detail

            itemView.setOnClickListener {
                if (!isDevelopAccessibilityServiceEnable()) {
                    //未打开开发者障碍服务
                    showAccessibilityDialog()
                    return@setOnClickListener
                }
                if (!checkDevelopSettings()) {
                    //未打开开发者选项开关
                    showSnackBar(this@DevelopHelpActivity,
                            " 开发者选项 未打开",
                            "去打开",
                            View.OnClickListener {
                                openDevelopSetting(DevelopData().apply {
                                    info = DevelopAction.DEVELOP_SETTINGS
                                })
                            })
                    return@setOnClickListener
                }
                when (entity.info) {
                    DevelopAction.DEVELOP_SETTINGS -> {
                        if (checkDevelopSettings()) {
                            // 该设置项已经打开
                            showSnackBar(this@DevelopHelpActivity,
                                    " ${entity.info} 已经打开",
                                    "去关闭",
                                    View.OnClickListener {
                                        // 去关闭
                                        Logger.d("close ")
                                        openDevelopSetting(entity)
                                    })
                        } else {
                            Logger.d("go to open")
                            openDevelopSetting(entity)
                        }
                    }
                    DevelopAction.USB_DEBUG_ONE -> {
                        if (checkUsbDebugSettings()) {
                            // 该设置项已经打开
                            showSnackBar(this@DevelopHelpActivity,
                                    " ${entity.info} 已经打开",
                                    "去关闭",
                                    View.OnClickListener {
                                        // 去关闭
                                        Logger.d("close ")
                                        openDevelopSetting(entity)
                                    })
                        } else {
                            Logger.d("go to open")
                            openDevelopSetting(entity)
                        }
                    }
                    else -> {
                        openDevelopSetting(entity)
                    }

                }
            }
        }

        private fun openDevelopSetting(entity: DevelopData) {
            DevelopSettings.isAuto = true
            DevelopSettings.developData = entity
            val intent = Intent()
            val componentName = ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings")
            intent.component = componentName
            startActivity(intent)

        }


        private fun showAccessibilityDialog() {
            AlertDialog.Builder(this@DevelopHelpActivity).setTitle("无障碍服务")
                    .setMessage(R.string.string_apply_develop_tips)
                    .setNegativeButton(R.string.cancle, null)
                    .setPositiveButton(R.string.comfirm, { _, which ->
                        Logger.d("to open" + which)
                        //跳转到无障碍服务页面
                        openAccessibilitySetting(this@DevelopHelpActivity)
                    })
                    .show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

val actionList = arrayListOf(
        DevelopData(),
        DevelopData().apply {
            info = DevelopAction.USB_DEBUG_ONE
            detail = "启动Adb接口调试"

        },
        DevelopData().apply {
            info = DevelopAction.STAY_AWAKE
            detail = "充电时屏幕不会休眠"

        },

        DevelopData().apply {
            info = DevelopAction.POINT_LOCATION
            detail = "屏幕叠加层显示触摸数据"

        },

        DevelopData().apply {
            info = DevelopAction.SHOW_LAYOUT_BOUNDS
            detail = "显示剪辑边界， 边距等"

        },
        DevelopData().apply {
            info = DevelopAction.DEBUG_GPU_OVERDRAW
            detail = "调试GPU过度绘制"

        },

        DevelopData().apply {
            info = DevelopAction.SHOW_CPU_USAGE
            detail = "在屏幕叠加层显示当前CPU信息"
        }
)


