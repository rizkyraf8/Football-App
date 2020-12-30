package com.rafcode.schedulefootball.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.databinding.RowMatchBinding
import com.rafcode.schedulefootball.ui.activity.BaseActivity
import com.rafcode.schedulefootball.ui.activity.DetailMatchActivity
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(
    private var list: ArrayList<Event>,
    private var type: String
) : BaseAdapter<Event, RowMatchBinding>(list) {

    override fun layout(): Int {
        return R.layout.row_match
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder<RowMatchBinding>,
        position: Int
    ) {
        val item = list[position]
        binding.event = item

        if (type == "next" || item.intHomeScore == null) {
            binding.ivAlarm.visibility = View.VISIBLE
        } else {
            binding.ivAlarm.visibility = View.GONE
        }

        if (item.intHomeScore == null && item.intAwayScore == null) {
            binding.tvScore.text = "VS"
        } else {
            binding.tvScore.text =
                item.intHomeScore.toString() + " - " + item.intAwayScore.toString()
        }

        val jam = item.strTime?.split("+")

        val dateString = item.dateEvent + " " + (jam?.get(0)?.substring(0, 5) ?: "00:00")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val convertedDate = dateFormat.parse(dateString)

        convertedDate?.time?.let {
            binding.tvTitle.text = (mContext as BaseActivity<*>).formatDate(it)
            binding.tvJam.text = (mContext as BaseActivity<*>).formatJam(it)
        }

        binding.ivAlarm.setOnClickListener {
            convertedDate?.let {
                (mContext as BaseActivity<*>).addToCalendar(item, it)
            }
        }

        holder.itemView.setOnClickListener {
            val i = Intent(mContext, DetailMatchActivity::class.java)
            i.putExtra("event", item)
            mContext.startActivity(i)
        }
    }
}

