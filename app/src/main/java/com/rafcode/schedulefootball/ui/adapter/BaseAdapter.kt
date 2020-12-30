package com.rafcode.schedulefootball.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, B : ViewDataBinding>(
    private var list: ArrayList<T>
) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>() {

    var onClickListener: OnItemClickListener<T>? = null
    lateinit var mContext: Context
    lateinit var binding: B

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    fun setListener(listener: OnItemClickListener<T>) {
        this.onClickListener = listener
    }

    fun reCreate(list: ArrayList<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun reCreate(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<T> {
        return list
    }

    fun getItem(postion: Int): T {
        return list[postion]
    }

    fun addItem(list: T) {
        this.list.add(list)
        notifyDataSetChanged()
    }

    fun addItem(list: List<T>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

    abstract fun layout(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        mContext = parent.context

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layout(),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder<B : ViewDataBinding>(binding: B) : RecyclerView.ViewHolder(binding.root)
}