package com.swensun.swdesign.ui.recycler

import android.app.ActivityOptions
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.viewmodel.RecyclerViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import java.util.concurrent.TimeUnit

class RecyclerViewActivity : AppCompatActivity(), LifecycleRegistryOwner {



    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    val adapter = RecyclerViewAdapter(this)
    var datas = arrayListOf<DoubanMovieEntity>()
    val viewModel: RecyclerViewModel by lazy {
        ViewModelProviders.of(this).get(RecyclerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        recycler_view.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
//        recycler_view.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_recycler_view)

        swipe_refresh_layout_recycler_view.setOnRefreshListener {
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
//                        adapter.setItemList(datas)

                        swipe_refresh_layout_recycler_view.isRefreshing = false
                        recycler_view.scheduleLayoutAnimation()
                    }
        }
        viewModel.moviesLiveData.observe(this, Observer {
            it?.let {
                adapter.setItemList(it)
                recycler_view.scheduleLayoutAnimation()
            }
        })
        viewModel.queryAllMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_recycler, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = menuItem.actionView as SearchView
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

        var mItemList = arrayListOf<DoubanMovieEntity>()

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

        fun setItemList(datas: List<DoubanMovieEntity>) {
            mItemList.clear()
            mItemList.addAll(datas)   //list的copy
            notifyDataSetChanged()
        }
    }

    inner class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.view_recycler_image)
        val movieKey: TextView =  itemView.findViewById(R.id.movie_key)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieScore: TextView = itemView.findViewById(R.id.score_content)

        fun updateView(entity: DoubanMovieEntity) {
            Glide.with(this@RecyclerViewActivity).load(entity.image).into(imageView)
            movieTitle.text = entity.title
            movieKey.text = entity.oriTitle
            movieScore.text = entity.score
            itemView.setOnClickListener {
                viewModel.setDoubanMovieEntity(entity)
                val intent = Intent(this@RecyclerViewActivity, ScrollingActivity::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@RecyclerViewActivity,
                            imageView, "shareView").toBundle())
                }
            }
        }
    }

    class FooterItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

enum class ViewHolderType {
    NORMAL, FOOTER
}




