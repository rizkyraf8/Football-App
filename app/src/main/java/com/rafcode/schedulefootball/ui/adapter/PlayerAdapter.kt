package com.rafcode.schedulefootball.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.databinding.RowPlayerBinding
import com.rafcode.schedulefootball.ui.activity.PlayerDetailActivity
import com.squareup.picasso.Picasso

class PlayerAdapter(private var list: ArrayList<Player>) :
    BaseAdapter<Player, RowPlayerBinding>(list) {

    override fun layout(): Int {
        return R.layout.row_player
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: BaseAdapter.ViewHolder<RowPlayerBinding>, position: Int) {
        val item = list[position]

        binding.tvPlayer.text = item.strPlayer

        Picasso.with(mContext).load(item.strThumb).into(binding.ivPlayer)

        holder.itemView.setOnClickListener {
            val i = Intent(mContext, PlayerDetailActivity::class.java)
            i.putExtra("player", item)
            mContext.startActivity(i)
        }

    }
}

