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
                        Timber.d("position: $position")
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
