package com.rafcode.schedulefootball.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.ui.activity.DetailTeamActivity
import com.rafcode.schedulefootball.utils.TempData
import com.squareup.picasso.Picasso

class TeamAdapter(private val context: Context, private var team: ArrayList<Team>) :
        RecyclerView.Adapter<TeamAdapter.MyViewHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val result = v?.tag as Team

        TempData.teams = result

        val i = Intent(context, DetailTeamActivity::class.java)
        context.startActivity(i)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTeam: TextView = view.findViewById(R.id.tvTeam)
        var ivTeam: ImageView = view.findViewById(R.id.ivTeam)

    }

    fun clear() {
        this.team.clear()
        notifyDataSetChanged()
    }

    fun reCreate(team: Team) {
        this.team.add(team)
        notifyItemInserted(itemCount)
    }

    fun addAll(team: ArrayList<Team>) {
        this.team = team
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_team, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = team[position]

        holder.tvTeam.text = data.strTeam

        if (data.strTeamBadge != null) Picasso.with(context).load(data.strTeamBadge).into(holder.ivTeam)

        holder.itemView.tag = data
        holder.itemView.setOnClickListener(this)

    }

    override fun getItemCount(): Int {
        return team.size
    }

}

