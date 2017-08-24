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
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import com.swensun.swdesign.base.dp2px
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dialog.*
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class DialogFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_dialog_1.setOnClickListener {

            val input = EditText(context)
            val frameLayout = FrameLayout(context)
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            lp.leftMargin = dp2px(20f)
            lp.rightMargin = dp2px(20f)
            input.layoutParams = lp
            frameLayout.addView(input)

            AlertDialog.Builder(context).setTitle("简单对话框")
//                    .setMessage("这是一个简单对话框")
                    .setPositiveButton("确认", null)
                    .setView(frameLayout)
                    .show()
        }
        btn_dialog_2.setOnClickListener {
            AlertDialog.Builder(context).setTitle("简单对话框").setMessage("这是一个可取消的简单对话框")
                    .setPositiveButton("确认", null)
                    .setNegativeButton("取消", null)
                    .show()
        }
        btn_dialog_3.setOnClickListener {
            val titleList = arrayOf("卡片", "对话框", "系统控件")
//            AlertDialog.Builder(context)
//                    .setTitle("单选对话框")
//                    .setSingleChoiceItems(titleList, 0, { dialogInterface, i ->
//                        Logger.d(titleList[i])
//                        dialogInterface.dismiss()
//                    })
//                    .setNegativeButton("取消", null)
//                    .show()
            AlertDialog.Builder(context)
                    .setTitle("单选对话框")
                    .setItems(titleList, { dialog, which ->
                        Logger.d(titleList[which])
                        dialog.dismiss()
                    })
                    .setNegativeButton("取消", null)
                    .show()
        }
        btn_dialog_4.setOnClickListener {
            val titleList = arrayOf("卡片", "对话框", "系统控件")
            val checkedItems = booleanArrayOf(true, false, false)
            AlertDialog.Builder(context)
                    .setTitle("多选对话框")
                    .setMultiChoiceItems(titleList, checkedItems, { _, i, isChecked ->
                        Logger.d(titleList[i]  + "--" + isChecked)
                    })
                    .setPositiveButton("确认", null)
                    .setNegativeButton("取消", null)
                    .show()
        }
        btn_dialog_5.setOnClickListener {
            ProgressDialog(context).apply {
                setMessage("加载中...")
            }.show()
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
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                Logger.d(date)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
        btn_dialog_8.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
                calendar.set(Calendar.HOUR_OF_DAY, i)
                calendar.set(Calendar.MINUTE, i1)
                Logger.d("$i : $i1")
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.show()
        }
        btn_dialog_9.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_sheet, null)
            val okBtn = dialogView.findViewById(R.id.ok_btn) as Button
            okBtn.setOnClickListener { bottomSheetDialog.dismiss() }
            val cancelBtn = dialogView.findViewById(R.id.cancel_btn) as Button
            cancelBtn.setOnClickListener { bottomSheetDialog.dismiss() }
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.show()
        }
        btn_dialog_10.setOnClickListener {
            val fullDialog = Dialog(context, R.style.DialogFullscreen)
            fullDialog.setContentView(R.layout.dialog_full_screen)
            val closeView = fullDialog.findViewById(R.id.img_dialog_fullscreen_close)
            closeView.setOnClickListener {
                fullDialog.dismiss()
            }
            fullDialog.show()
        }
        btn_dialog_11.setOnClickListener {

        }
    }
}
