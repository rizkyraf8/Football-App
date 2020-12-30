package com.rafcode.schedulefootball.ui.activity

import androidx.databinding.DataBindingUtil
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.databinding.ActivityPlayerDetailBinding
import com.squareup.picasso.Picasso

class PlayerDetailActivity : BaseActivity<ActivityPlayerDetailBinding>() {

    private val player: Player? by lazy {
        intent.getParcelableExtra<Player>("player")
    }

    override fun getViewBinding(): ActivityPlayerDetailBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_player_detail)
    }

    override fun onActivityCreated() {
        initToolbar()
    }

    override fun onActivityClick() {

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = player?.strPlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        Picasso.with(this).load(player?.strFanart1).into(binding.layout.ivWall)

        binding.layout.player = player
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
