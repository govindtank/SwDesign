package com.swensun.swdesign.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swensun.swdesign.R

/**
 * Created by swensun on 2017/8/12.
 */

class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mItemList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when(viewType) {
            ViewHolderType.NORMAL.ordinal -> {
                val view = LayoutInflater.from(context).inflate(R.layout.view_normal_item, parent, false)
                return NormalItemViewHolder(itemView = view)
            }
        }
        val view = LayoutInflater.from(context).inflate(R.layout.view_footer_item, parent, false)
        return FooterItemHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ViewHolderType.FOOTER.ordinal -> {
                return
            }
        }
        (holder as NormalItemViewHolder).updateView(mItemList[position])
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == mItemList.size - 1) {
            return ViewHolderType.FOOTER.ordinal
        }
        return ViewHolderType.NORMAL.ordinal
    }

    fun setItemList(datas: List<String>) {
        mItemList.addAll(datas)   //list的copy
        notifyDataSetChanged()
    }

    fun addItem(position: Int, str: String) {
        mItemList.add(position, str)
        notifyItemInserted(position)
    }

    fun addItemLists(datas: List<String>) {
        mItemList.addAll(datas)
        notifyItemInserted(mItemList.size - 1)
    }

    fun addFooter() {
        mItemList.add("load more")
        notifyItemInserted(mItemList.size - 1)
    }

    fun removeFooter() {
        mItemList.removeAt(mItemList.size - 1)
        notifyItemRemoved(mItemList.size)
    }

    class NormalItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById(R.id.textView) as TextView
        fun updateView(s: String) {
            textView.text = s
        }
        init {
            itemView.setOnClickListener {

            }
        }
    }
    class FooterItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    enum class ViewHolderType {
        NORMAL, FOOTER
    }
}
