package com.rafcode.schedulefootball.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.ui.activity.DetailMatchActivity
import com.rafcode.schedulefootball.utils.TempData
import java.text.SimpleDateFormat
import java.util.*


class MatchAdapter(private val context: Context, private var event: ArrayList<Event>, private var type: String) : RecyclerView.Adapter<MatchAdapter.MyViewHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val result = v?.tag as Event

        TempData.event = result

        val i = Intent(context, DetailMatchActivity::class.java)
        context.startActivity(i)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvScore: TextView = view.findViewById(R.id.tvScore)
        var tvTeamHome: TextView = view.findViewById(R.id.tvTeamHome)
        var tvTeamAway: TextView = view.findViewById(R.id.tvTeamAway)
        var tvJam: TextView = view.findViewById(R.id.tvJam)
        var ivAlarm: ImageButton = view.findViewById(R.id.ivAlarm)

    }

    fun clear() {
        this.event.clear()
        notifyDataSetChanged()
    }

    fun reCreate(event: Event) {
        this.event.add(event)
        notifyItemInserted(itemCount)
    }

    fun addAll(event: ArrayList<Event>) {
        this.event = event
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_match, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = event[position]

        if (type == "next" || data.intHomeScore == null) {
            holder.ivAlarm.visibility = View.VISIBLE
        } else {
            holder.ivAlarm.visibility = View.GONE
        }

        holder.tvTeamHome.text = data.strHomeTeam
        holder.tvTeamAway.text = data.strAwayTeam

        if (data.intHomeScore == null && data.intAwayScore == null) {
            holder.tvScore.text = "VS"
        } else {
            holder.tvScore.text = data.intHomeScore.toString() + " - " + data.intAwayScore.toString()
        }

        val jam = data.strTime?.split("+")

        val dateString = data.dateEvent + " " + (jam?.get(0)?.substring(0,5) ?: "00:00")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val convertedDate = dateFormat.parse(dateString)


        holder.tvTitle.text = formatDate(convertedDate.time)
        holder.tvJam.text = formatJam(convertedDate.time)

        holder.itemView.tag = data
        holder.itemView.setOnClickListener(this)

        holder.ivAlarm.setOnClickListener {
            addToCalendar(data, convertedDate)
        }

    }

    override fun getItemCount(): Int {
        return event.size
    }

    fun formatDate(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    fun formatJam(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    fun addToCalendar(data: Event, convertedDate: Date) {
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertedDate.time)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, convertedDate.time)
                .putExtra(CalendarContract.Events.TITLE, data.strHomeTeam + " VS " + data.strAwayTeam)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, data.strCountry.toString())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        context.startActivity(intent)
    }
}

