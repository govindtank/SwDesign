package com.swensun.swdesign.ui.recycler

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ImageView
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

    inner class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var mItemList = arrayListOf<Int>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            val view = LayoutInflater.from(context).inflate(R.layout.view_normal_item, parent, false)
            return NormalItemViewHolder(itemView = view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as NormalItemViewHolder).updateView(mItemList[position])
        }

        override fun getItemCount(): Int {
            return mItemList.size
        }

        override fun getItemViewType(position: Int): Int {
            return ViewHolderType.NORMAL.ordinal
        }

        fun setItemList(datas: List<Int>) {
            mItemList.clear()
            mItemList.addAll(datas)   //list的copy
            notifyDataSetChanged()
        }


    }

    inner class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById(R.id.view_recycler_image) as ImageView
//        val textView = itemView.findViewById(R.id.view_recycler_text) as TextView

        fun updateView(drawable: Int) {
//            Glide.with(context).load(drawable).into(imageView)
//            textView.text = "这是第 $adapterPosition 张图片"

        }
        init {
            itemView.setOnClickListener {
                val intent = Intent(this@RecyclerViewActivity, ScrollingActivity::class.java)
                intent.putExtra("listID", adapterPosition)
//                context.startActivity(intent)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@RecyclerViewActivity,
                        imageView, "shareView").toBundle())
            }
        }
    }

    class FooterItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

enum class ViewHolderType {
    NORMAL, FOOTER
}




