package com.swensun.swdesign.ui

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.swensun.swdesign.R

/**
 * Created by swensun on 2017/8/12.
 */

class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mItemList = arrayListOf<Int>()
    val LIST_ID = "listID"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
//        when(viewType) {
//            ViewHolderType.NORMAL.ordinal -> {
//                val view = LayoutInflater.from(context).inflate(R.layout.view_normal_item, parent, false)
//                return NormalItemViewHolder(itemView = view)
//            }
//        }
//        val view = LayoutInflater.from(context).inflate(R.layout.view_footer_item, parent, false)
//        return FooterItemHolder(itemView = view)
        val view = LayoutInflater.from(context).inflate(R.layout.view_normal_item, parent, false)
        return NormalItemViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when(holder.itemViewType) {
//            ViewHolderType.FOOTER.ordinal -> {
//                return
//            }
//        }
        (holder as NormalItemViewHolder).updateView(mItemList[position])
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun getItemViewType(position: Int): Int {
//        if (position == mItemList.size - 1) {
//            return ViewHolderType.FOOTER.ordinal
//        }
        return ViewHolderType.NORMAL.ordinal
    }

    fun setItemList(datas: List<Int>) {
        mItemList.clear()
        mItemList.addAll(datas)   //list的copy
        notifyDataSetChanged()
    }

    inner class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById(R.id.view_recycler_image) as ImageView
        val textView = itemView.findViewById(R.id.view_recycler_text) as TextView

        fun updateView(drawable: Int) {
            Glide.with(context).load(drawable).into(imageView)
            textView.text = "这是第 $adapterPosition 张图片"

        }
        init {
            itemView.setOnClickListener {
                val intent = Intent(context, ScrollingActivity::class.java)
                intent.putExtra(LIST_ID, adapterPosition)
                context.startActivity(intent)
            }
        }
    }
    class FooterItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    enum class ViewHolderType {
        NORMAL, FOOTER
    }
}
