package com.rafcode.schedulefootball.ui.activity

import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.databinding.ActivityDetailTeamBinding
import com.rafcode.schedulefootball.ui.fragment.TeamListPlayerFragment
import com.rafcode.schedulefootball.ui.fragment.TeamOverviewFragment
import com.rafcode.schedulefootball.utils.ViewPagerAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTeamActivity : BaseActivity<ActivityDetailTeamBinding>() {

    private val team: Team? by lazy {
        intent.getParcelableExtra<Team>("team")
    }

    override fun getViewBinding(): ActivityDetailTeamBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_detail_team)
    }

    override fun onActivityCreated() {
        initToolbar()

        Picasso.with(activity).load(team?.strTeamBadge).into(binding.ivTeam)
        binding.team = team
        setupViewPager()
        binding.layout.tlTeam.setupWithViewPager(binding.layout.vpTeam)
    }

    override fun onActivityClick() {

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.toolbarLayout.title = team?.strTeam
                    isShow = true
                } else if (isShow) {
                    binding.toolbarLayout.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TeamOverviewFragment(team).getInstance(), "Overview")
        adapter.addFragment(TeamListPlayerFragment(team?.idTeam.toString()).getInstance(), "Player")
        binding.layout.vpTeam.adapter = adapter
        binding.layout.vpTeam.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                val fragment = adapter.instantiateItem(binding.layout.vpTeam, position) as Fragment
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)

        database.use {
            select(FavoriteTeam.TABLE_TEAM)
                .whereArgs("${FavoriteTeam.idTeam} = ${team?.idTeam}").exec {
                    if (count == 0) {
                        menu?.getItem(0)?.icon =
                            ContextCompat.getDrawable(
                                activity,
                                R.drawable.ic_star_border_black_24dp
                            )
                    } else {
                        menu?.getItem(0)?.icon =
                            ContextCompat.getDrawable(activity, R.drawable.ic_star_black_24dp)
                    }

                }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_fav) {
            database.use {
                select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("${FavoriteTeam.idTeam} = ${team?.idTeam}").exec {
                        if (count == 0) {
                            insert(
                                FavoriteTeam.TABLE_TEAM,
                                FavoriteTeam.idTeam to team?.idTeam,
                                FavoriteTeam.gson to Gson().toJson(team)
                            )

                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Berhasil menambahkan team favorit",
                                2000
                            ).show()
                            item.icon =
                                ContextCompat.getDrawable(activity, R.drawable.ic_star_black_24dp)
                        } else {
                            delete(
                                FavoriteTeam.TABLE_TEAM,
                                FavoriteTeam.idTeam + " = " + team?.idTeam
                            )
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Berhasil hapus team favorit",
                                2000
                            ).show()
                            item.icon = ContextCompat.getDrawable(
                                activity,
                                R.drawable.ic_star_border_black_24dp
                            )
                        }
                    }
            }


            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
