package com.rafcode.schedulefootball.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.utils.TempData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.content_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {

    var player: Player = TempData.player!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        initToolbar()


    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = player.strPlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        Picasso.with(this).load(player.strFanart1).into(ivWall)
        tvWeight.text = player.strWeight
        tvHeight.text = player.strHeight
        tvPosisi.text = player.strPosition
        tvDesc.text = player.strDescriptionEN
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
