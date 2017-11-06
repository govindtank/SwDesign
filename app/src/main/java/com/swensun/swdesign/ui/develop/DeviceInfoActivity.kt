package com.swensun.swdesign.ui.develop

import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swensun.swdesign.R
import com.swensun.swutils.util.getDisplayMetrics
import com.swensun.swutils.util.getMemInfo
import com.swensun.swutils.util.getSDAvaildableSize
import com.swensun.swutils.util.getSDTotalSize
import kotlinx.android.synthetic.main.activity_device_info.*
import kotlinx.android.synthetic.main.content_device_info.*

class DeviceInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        cdi_recycler_device.layoutManager = LinearLayoutManager(this)
        cdi_recycler_device.setHasFixedSize(true)
        cdi_recycler_device.adapter = RecyclerViewAdapter(this)
    }

    inner class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var mItemList = arrayListOf("系统信息", "屏幕信息", "硬件信息", "网络状态", "应用列表")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            val view = LayoutInflater.from(context).inflate(R.layout.view_device_info_item, parent, false)
            return NormalItemViewHolder(itemView = view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as NormalItemViewHolder).updateView(mItemList[position])
        }

        override fun getItemCount(): Int {
            return mItemList.size
        }

    }

    inner class NormalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val infoView: TextView = itemView.findViewById(R.id.vdii_tv_info)
        private val detailView: TextView = itemView.findViewById(R.id.vdii_tv_detail)

        fun updateView(action: String) {
            infoView.text = action
            detailView.visibility = View.GONE
            itemView.setOnClickListener {
                when (adapterPosition) {
                    0 -> {
                        showVersionInfo()
                    }
                    1 -> {
                        showScreenInfo()
                    }
                    2 -> {}
                    3 -> {
                        showNetWorkInfo()
                    }
                    4 -> {
                        showInstallAapps()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showInstallAapps() {
        val apps = packageManager.getInstalledPackages(0)
        val names = arrayListOf<String>()
        apps.forEach {
            if (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0) {
                names.add(it.applicationInfo.loadLabel(packageManager).toString())
            }
        }
        AlertDialog.Builder(this)
                .setTitle("应用列表")
                .setItems(names.toTypedArray(), { _, _ -> })
                .setNegativeButton("取消", null)
                .show()
    }

    private fun showNetWorkInfo() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        var netName = ""
        var netConnectName = ""
        if (networkInfo != null && networkInfo.isAvailable) {
            netName = networkInfo.typeName
            if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                netConnectName = networkInfo.subtypeName
            } else if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                wifiInfo?.let {
                    netConnectName = it.ssid
                }
            } else {
                netName = "unknown"
                netConnectName = "unknown"
            }
        } else {
            //无网络
            netName = "disconnect"
            netConnectName = "null"
        }

        val info = "网络连接：$netName \n" +
                "连接名称：$netConnectName "
        AlertDialog.Builder(this@DeviceInfoActivity).setTitle("网络状态").setMessage(info)
                .setPositiveButton("分享", null)
                .setNegativeButton("复制", null)
                .show()

    }

    private fun showVersionInfo() {
        val model = Build.MODEL
        val manufacture = Build.BRAND
        val release = Build.VERSION.RELEASE
        val sdk_int = Build.VERSION.SDK_INT

        val path = Environment.getExternalStorageDirectory()
        val stas = StatFs(path.path)
        val blockSize = stas.blockSizeLong

        val totalBlocks = stas.blockCountLong
        val availableBlocks = stas.availableBlocksLong
        val totalSize = Formatter.formatFileSize(this, blockSize * totalBlocks)
        val availdSize = Formatter.formatFileSize(this, blockSize * availableBlocks)
        getMemInfo()

        val info = "制造商： $manufacture \n" +
                "手机型号： $model \n" +
                "版本号： $release \n" +
                "API版本： $sdk_int \n" +
                "运行内存: ${ getMemInfo()} \n" +
                "SD内存： ${getSDAvaildableSize()} / ${getSDTotalSize()} \n"
        AlertDialog.Builder(this@DeviceInfoActivity).setTitle("版本信息").setMessage(info)
                .setPositiveButton("分享", null)
                .setNegativeButton("复制", null)
                .show()
    }

    private fun showScreenInfo() {
        val displayMetrics = getDisplayMetrics()
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val densityDpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        val realXdpi = displayMetrics.xdpi
        val realYdpi = displayMetrics.ydpi
        val dpi: String = when (density.toInt()) {
            2 ->{ "xhdpi"}
            3 ->{"xxhdpi"}
            4 ->{"xxxhdpi"}
            else -> {"no dpi"}
        }

        val physicsWidth = Math.round( width / realXdpi * 10) / 10f
        val physicsHeight = Math.round( height / realYdpi * 10) / 10f

        val tempInch = Math.sqrt(((physicsHeight * physicsHeight +
                physicsWidth * physicsWidth).toDouble())
        )
        val physicsInch =  Math.round( tempInch * 10) / 10f

        val info = "屏幕分辨率：$width * $height px \n" +
                "设备密度：$densityDpi dp / $dpi  / $density x \n" +
                "实际密度：$realXdpi dp / $realYdpi dp \n" +
                "物理尺寸：$physicsWidth * $physicsHeight / $physicsInch 英寸"
        AlertDialog.Builder(this@DeviceInfoActivity).setTitle("屏幕信息").setMessage(info)
                .setPositiveButton("分享", null)
                .setNegativeButton("复制", null)
                .show()
    }
}
