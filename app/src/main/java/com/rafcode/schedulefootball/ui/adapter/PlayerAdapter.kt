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
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.ui.activity.PlayerDetailActivity
import com.rafcode.schedulefootball.utils.TempData
import com.squareup.picasso.Picasso

class PlayerAdapter(private val context: Context, private var player: ArrayList<Player>) :
        RecyclerView.Adapter<PlayerAdapter.MyViewHolder>(), View.OnClickListener {

    override fun onClick(v: View?) {
        val result = v?.tag as Player

        TempData.player = result

        val i = Intent(context, PlayerDetailActivity::class.java)
        context.startActivity(i)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvPlayer: TextView = view.findViewById(R.id.tvTeam)
        var ivPlayer: ImageView = view.findViewById(R.id.ivTeam)

    }

    fun clear() {
        this.player.clear()
        notifyDataSetChanged()
    }

    fun reCreate(player: Player) {
        this.player.add(player)
        notifyItemInserted(itemCount)
    }

    fun addAll(player: ArrayList<Player>) {
        this.player = player
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_team, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = player[position]

        holder.tvPlayer.text = data.strPlayer

        Picasso.with(context).load(data.strThumb).into(holder.ivPlayer)

        holder.itemView.tag = data
        holder.itemView.setOnClickListener(this)

    }

    override fun getItemCount(): Int {
        return player.size
    }

}

