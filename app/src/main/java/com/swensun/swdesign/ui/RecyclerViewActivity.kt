package com.swensun.swdesign.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.swensun.swdesign.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import java.util.concurrent.TimeUnit

class RecyclerViewActivity : AppCompatActivity() {

    val adapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

        swipe_refresh_layout_recycler_view.setOnRefreshListener {
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        swipe_refresh_layout_recycler_view.isRefreshing = false
                    }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<NormalItemViewHolder>() {
        override fun onBindViewHolder(holder: NormalItemViewHolder?, position: Int) {

        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NormalItemViewHolder {
            val view = layoutInflater.inflate(R.layout.view_normal_item, parent, false)
            return NormalItemViewHolder(itemView = view)
        }

    }

    class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}




