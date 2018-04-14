package com.swensun.swdesign.ui.viewpager


import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.swensun.swdesign.R
import com.swensun.swutils.util.dp2px
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dialog.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class DialogFragment : Fragment() {


    var isVisibleToUser = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timber.d(isVisibleToUser.toString())
//        if (!isVisibleToUser) return
        btn_dialog_1.setOnClickListener {

            val input = EditText(context)
            val frameLayout = FrameLayout(context)
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            lp.leftMargin = dp2px(20f)
            lp.rightMargin = dp2px(20f)
            input.layoutParams = lp
            frameLayout.addView(input)

            context?.let {
                AlertDialog.Builder(it).setTitle("简单对话框")
    //                    .setMessage("这是一个简单对话框")
                        .setPositiveButton("确认", null)
                        .setView(frameLayout)
                        .show()
            }
        }
        btn_dialog_2.setOnClickListener {
            context?.let { it1 ->
                AlertDialog.Builder(it1).setTitle("简单对话框").setMessage("这是一个可取消的简单对话框")
                        .setPositiveButton("确认", null)
                        .setNegativeButton("取消", null)
                        .show()
            }
        }
        btn_dialog_3.setOnClickListener {
            val titleList = arrayOf("卡片", "对话框", "系统控件")
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                        .setTitle("单选对话框")
                        .setItems(titleList, { dialog, which ->
                            Timber.d(titleList[which])
                            dialog.dismiss()
                        })
                        .setNegativeButton("取消", null)
                        .show()
            }
        }
        btn_dialog_4.setOnClickListener {
            val titleList = arrayOf("卡片", "对话框", "系统控件")
            val checkedItems = booleanArrayOf(true, false, false)
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                        .setTitle("多选对话框")
                        .setMultiChoiceItems(titleList, checkedItems, { _, i, isChecked ->
                            Timber.d(titleList[i]  + "--" + isChecked)
                        })
                        .setPositiveButton("确认", null)
                        .setNegativeButton("取消", null)
                        .show()
            }
        }
        btn_dialog_5.setOnClickListener {
//            ProgressBar(context).apply {
//                setMessage("加载中...")
//            }.show()
            val progressBar = ProgressBar(context, null, R.attr.indeterminateProgressStyle)
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                        .setMessage("加载中...")
                        .setView(R.layout.dialog_progress)
                        .show()
            }

        }
        btn_dialog_6.setOnClickListener {
           val progressDialog =  ProgressDialog(context).apply {
                setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                setMessage("加载中...")
                setCancelable(false)
                max = 100
            }
            progressDialog.show()
            var dis: Disposable? = null
            var progress = 25
            Observable.interval(30, TimeUnit.MILLISECONDS)
                    .doOnSubscribe { dis = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (progressDialog.progress == 100) {
                            dis?.dispose()
                            progressDialog.dismiss()
                        }
                        progressDialog.progress = progress++
                    }
        }
        btn_dialog_7.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                Timber.d(date)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
        btn_dialog_8.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, i, i1 ->
                calendar.set(Calendar.HOUR_OF_DAY, i)
                calendar.set(Calendar.MINUTE, i1)
                Timber.d("$i : $i1")
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.show()
        }
        btn_dialog_9.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context!!)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_sheet, null)
            val okBtn: Button = dialogView.findViewById(R.id.ok_btn)
            okBtn.setOnClickListener { bottomSheetDialog.dismiss() }
            val cancelBtn: Button = dialogView.findViewById(R.id.cancel_btn)
            cancelBtn.setOnClickListener { bottomSheetDialog.dismiss() }
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.show()
        }
        btn_dialog_10.setOnClickListener {
            val fullDialog = Dialog(context, R.style.DialogFullscreen)
            fullDialog.setContentView(R.layout.dialog_full_screen)
            val closeView: ImageView = fullDialog.findViewById(R.id.img_dialog_fullscreen_close)
            closeView.setOnClickListener {
                fullDialog.dismiss()
            }
            fullDialog.show()
        }
        btn_dialog_11.setOnClickListener {

        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        // 控制fragment可见，每次tab切换都会调用
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        Timber.d(isVisibleToUser.toString())
    }
}
