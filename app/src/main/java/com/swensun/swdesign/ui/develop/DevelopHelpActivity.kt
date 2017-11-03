package com.swensun.swdesign.ui.develop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import com.swensun.swdesign.base.checkSettingsAble
import com.swensun.swdesign.base.isDevelopAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import com.swensun.swdesign.base.showSnackBar
import com.swensun.swdesign.service.DevelopAction
import com.swensun.swdesign.service.DevelopAction.DEVELOP_SETTINGS
import com.swensun.swdesign.service.DevelopData
import com.swensun.swdesign.service.DevelopSettings
import com.swensun.swdesign.service.OpenMark
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
        if (DevelopSettings.position != -1) {
            actionList[DevelopSettings.position].openable = DevelopSettings.itemSwitchOn
        }
        adaper.setItemList(actionList)
        cdh_recycler_develop.adapter = adaper
    }

    inner class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var mItemList = arrayListOf<DevelopData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
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

            when (entity.info) {
                DevelopAction.DEVELOP_SETTINGS, DevelopAction.USB_DEBUG_ONE -> {
                    if (checkSettingsAble(entity.settingsKey)) {
                        itemView.setBackgroundResource(R.color.colorPrimary)
                    }
                }
                else -> {
                    if (entity.openable == OpenMark.TRUE) {
                        itemView.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }

            itemView.setOnClickListener {
                if (!isDevelopAccessibilityServiceEnable()) {
                    //未打开开发者障碍服务
                    showAccessibilityDialog()
                } else {

                    when (entity.info) {
                        DevelopAction.USB_DEBUG_ONE, DevelopAction.DEVELOP_SETTINGS -> {
                            if (checkSettingsAble(entity.settingsKey)) {
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
                            when (entity.openable) {
                                OpenMark.NOTSET -> {
                                    // 直接去打开设置
                                    Logger.d("go to set")
                                    openDevelopSetting(entity)
                                }
                                OpenMark.FALSE -> {
                                    //去打开
                                    Logger.d("go to open")
                                    openDevelopSetting(entity)
                                }
                                OpenMark.TRUE -> {
                                    showSnackBar(this@DevelopHelpActivity,
                                            " ${entity.info} 已经打开",
                                            "去关闭",
                                            View.OnClickListener {
                                        // 去关闭
                                        Logger.d("close ")
                                        openDevelopSetting(entity)
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun openDevelopSetting(entity: DevelopData) {
            DevelopSettings.isAuto = true
            DevelopSettings.developData = entity
            DevelopSettings.position = adapterPosition
            DevelopSettings.itemSwitchOn = OpenMark.NOTSET
            val developSettingIntent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            developSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this@DevelopHelpActivity.startActivity(developSettingIntent)
        }


        private fun showAccessibilityDialog() {
            AlertDialog.Builder(this@DevelopHelpActivity).setTitle(DEVELOP_SETTINGS)
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
}

val actionList = arrayListOf(
        DevelopData().apply {
            if (checkSettingsAble(settingsKey)) {
                openable = OpenMark.TRUE
            }
        },
        DevelopData().apply {
            info = DevelopAction.USB_DEBUG_ONE
            detail = "启动Adb接口调试"
            settingsKey = Settings.Global.ADB_ENABLED
            if (checkSettingsAble(settingsKey)) {
                openable = OpenMark.TRUE
            }
        },
        DevelopData().apply {
            info = DevelopAction.STAY_AWAKE
            detail = "充电时屏幕不会休眠"
            settingsKey = Settings.Global.STAY_ON_WHILE_PLUGGED_IN
        },
        DevelopData().apply {
            info = DevelopAction.SHOW_TAPS
            detail = "显示点按操作的视觉反馈"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.POINT_LOCATION
            detail = "屏幕叠加层显示触摸数据"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.SHOW_SURFACE_UPDATE
            detail = "窗口中的surface更新时全部闪烁"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.SHOW_LAYOUT_BOUNDS
            detail = "显示剪辑边界， 边距等"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.DEBUG_GPU_OVERDRAW
            detail = "调试GPU过度绘制"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.STRICT_MODE_ENABLED
            detail = "应用在主线程执行长时间操作时闪烁屏幕"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.SHOW_CPU_USAGE
            detail = "在屏幕叠加层显示当前CPU信息"
            settingsKey = ""
        },
        DevelopData().apply {
            info = DevelopAction.PROFILE_GPU_RENDERING
            detail = "GPU 呈现模式分析"
            settingsKey = ""
        }
)


