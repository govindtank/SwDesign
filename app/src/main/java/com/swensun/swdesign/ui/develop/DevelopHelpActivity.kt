package com.swensun.swdesign.ui.develop

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import com.swensun.swdesign.base.checkDevelopSettings
import com.swensun.swdesign.base.isDevelopAccessibilityServiceEnable
import com.swensun.swdesign.base.openAccessibilitySetting
import com.swensun.swdesign.base.showSnackBar
import com.swensun.swdesign.ui.develop.DevelopAction.DEVELOP_SETTINGS
import kotlinx.android.synthetic.main.activity_develop_help.*
import kotlinx.android.synthetic.main.content_develop_help.*


object DevelopAction {
    val DEVELOP_SETTINGS = "开发者选项" //com.android.settings:id/switch_bar
    val USB_DEBUG_ONE = "USB调试"
    val USB_DEBUG_TWO = "Android 调试"
    val STAY_AWAKE = "不锁定屏幕"
    val SHOW_TAPS = "显示点按操作反馈"
    val POINT_LOCATION = "指针位置"
    val SHOW_SURFACE_UPDATE = "更新时全部闪烁"
    val SHOW_LAYOUT_BOUNDS = "显示布局边界"
    val DEBUG_GPU_OVERDRAW = "调试GPU过度绘制"
    val STRICT_MODE_ENABLED = "启动严格模式"
    val SHOW_CPU_USAGE = "显示GPU信息"
    val PROFILE_GPU_RENDERING = "GPU 呈现模式分析"
}

object DevelopSettings {
    var action = DevelopAction.DEVELOP_SETTINGS
    var isAutoSetting = false
}

class DevelopHelpActivity : AppCompatActivity() {

    var actionList = arrayListOf(DevelopAction.DEVELOP_SETTINGS,
            DevelopAction.USB_DEBUG_ONE,
            DevelopAction.STAY_AWAKE,
            DevelopAction.SHOW_TAPS,
            DevelopAction.POINT_LOCATION,
            DevelopAction.SHOW_SURFACE_UPDATE,
            DevelopAction.SHOW_LAYOUT_BOUNDS,
            DevelopAction.DEBUG_GPU_OVERDRAW,
            DevelopAction.STRICT_MODE_ENABLED,
            DevelopAction.SHOW_CPU_USAGE,
            DevelopAction.PROFILE_GPU_RENDERING
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_develop_help)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        val adaper = RecyclerViewAdapter(this)
        cdh_recycler_develop.layoutManager = LinearLayoutManager(this)
        adaper.setItemList(actionList)
        cdh_recycler_develop.adapter = adaper
        cdh_recycler_develop.setHasFixedSize(true)
//        develop.setOnClickListener {
//            if (isDevelopAccessibilityServiceEnable()) {
//                val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
//                startActivity(intent)
//            } else {
//                openAccessibilitySetting(this)
//            }
//        }
    }

    inner class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var mItemList = arrayListOf<String>()

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

        fun setItemList(datas: List<String>) {
            mItemList.clear()
            mItemList.addAll(datas)   //list的copy
            notifyDataSetChanged()
        }
    }

    inner class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val actionView: Button = itemView.findViewById(R.id.vdsi_tv_action)

        fun updateView(entity: String) {
            actionView.text = entity
            DevelopSettings.action = entity
            actionView.setOnClickListener {
                Logger.d(entity)
                if (!isDevelopAccessibilityServiceEnable()) {
                    //未打开开发者障碍服务
                    showAccessibilityDialog()
                } else {
                    if (checkDevelopSettings()) {
                        // 该设置项已经打开
                        showSnackBar(this@DevelopHelpActivity, " $entity 已经打开", "去关闭", View.OnClickListener {
                            // 去关闭
                            Logger.d("close ")
                        })
                    } else {
                        //跳转去自动打开设置项
                        Logger.d("open")
                    }
                }
            }
        }

        private fun showAccessibilityDialog() {
            AlertDialog.Builder(this@DevelopHelpActivity).setTitle(DEVELOP_SETTINGS)
                    .setMessage(R.string.string_apply_develop_tips)
                    .setNegativeButton(R.string.cancle, null)
                    .setPositiveButton(R.string.comfirm, { _, which ->
                        Logger.d("to open" +  which)
                        //跳转到开发者选项页面
                        openAccessibilitySetting(this@DevelopHelpActivity)
                    })
                    .show()
        }
    }
}
