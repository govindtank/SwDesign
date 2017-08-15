package com.swensun.swdesign.ui.recycler

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import com.swensun.swdesign.utils.recyclePictureList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import java.util.concurrent.TimeUnit

class RecyclerViewActivity : AppCompatActivity() {

    val adapter = RecyclerViewAdapter(this)
    var datas: List<Int>  =  recyclePictureList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "这是一个RecyclerView", Snackbar.LENGTH_LONG)
                    .show()
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

        swipe_refresh_layout_recycler_view.setOnRefreshListener {
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        datas.apply {
                            toSet().toList()
                        }
                        adapter.setItemList(datas)
                        swipe_refresh_layout_recycler_view.isRefreshing = false
                    }
        }
        adapter.setItemList(datas)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_recycler, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Logger.d(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Logger.d(newText)
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_search) {
//            TransitionManager.beginDelayedTransition(toolbar)
//            MenuItemCompat.expandActionView(item)
            return true
        }

        return super.onOptionsItemSelected(item)
    }



}




