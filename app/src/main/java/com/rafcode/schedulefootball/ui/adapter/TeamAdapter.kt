package com.rafcode.schedulefootball.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.databinding.RowTeamBinding
import com.rafcode.schedulefootball.ui.activity.DetailTeamActivity
import com.squareup.picasso.Picasso

class TeamAdapter(private val context: Context, private var list: ArrayList<Team>) :
    BaseAdapter<Team, RowTeamBinding>(list) {

    override fun layout(): Int {
        return R.layout.row_team
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder<RowTeamBinding>, position: Int) {
        val item = list[position]

        binding.tvTeam.text = item.strTeam

        if (item.strTeamBadge != null) Picasso.with(context).load(item.strTeamBadge)
            .into(binding.ivTeam)

        holder.itemView.tag = item
        holder.itemView.setOnClickListener {
            val i = Intent(context, DetailTeamActivity::class.java)
            i.putExtra("team", item)
            context.startActivity(i)
        }

    }
}

