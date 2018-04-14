package com.swensun.swdesign.ui.codeexample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swensun.swdesign.R
import com.swensun.swdesign.base.add5_3_1
import com.swensun.swdesign.base.add5_3_2
import com.swensun.swdesign.base.countDown
import com.swensun.swdesign.viewmodel.CodeExampleViewModel
import kotlinx.android.synthetic.main.activity_code_example.*
import kotlinx.android.synthetic.main.content_code_example.*
import org.jetbrains.anko.find
import timber.log.Timber

class CodeExampleActivity : AppCompatActivity() {

    val TAG = "CodeExampleActivity"

    lateinit var viewModel: CodeExampleViewModel
    val itemList = arrayListOf(
            "自定义AlertDialog完成Rxjava2倒计时",
            "简单介绍函数式编程和柯里化")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_example)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(CodeExampleViewModel::class.java)
        initView()
    }

    private fun initView() {
        cce_recycler.layoutManager = LinearLayoutManager(this)
        cce_recycler.setHasFixedSize(true)
        cce_recycler.adapter = Adapter()
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeBtn = itemView.find<TextView>(R.id.vice_btn_code)
        fun bindData(position: Int) {
            codeBtn.text = itemList[position]
            codeBtn.setOnClickListener {
                when (position) {
                    0 -> {
                        codeBtn.countDown()
                    }
                    1 -> {
//                        Timber.d("函数对象计算结果：${add3(3, 5)}")
//                        val result = calculate(2, 3, add3)
//                        Timber.d("计算结果为: $result")
//                        val addFour = addFour()
//                        Timber.d("+ 5:${addFour(5)}")
//                        Timber.d("+ 6:${addFour(6)}")

//                        val addF = add7()
//
//                        val addFF = add8()
//                        Timber.d("函数add4(): ${add7()}")
//                        Timber.d("函数对象addF: $addF")
//
//                        Timber.d("函数add5(): ${add8()}")
//                        Timber.d("函数addFF: $addFF")
//
//                        Timber.d("函数add9: $add9")
//
//                        Timber.d("函数对象addF计算结果：${addF(1, 2)}")
//                        Timber.d("函数add7()计算结果: ${add7()(3, 4)}")
//
//
//                        Timber.d("函数addFF计算结果: ${addFF(4, 5)}")
//                        Timber.d("函数add8()计算结果: ${add8()(6, 7)}")

//                        val addF = add5()
//                        val addFF = add8()


                        Timber.d("${add5_3_1()(15)}")
                        Timber.d("${add5_3_2()()(15)}")
                    }
                }
            }
        }
    }

    inner class Adapter : RecyclerView.Adapter<ItemViewHolder>() {
        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = layoutInflater.inflate(R.layout.view_item_code_example, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.bindData(position)
        }
    }

}
