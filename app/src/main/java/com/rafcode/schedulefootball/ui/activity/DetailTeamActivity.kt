package com.rafcode.schedulefootball.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.ui.fragment.TeamListPlayerFragment
import com.rafcode.schedulefootball.ui.fragment.TeamOverviewFragment
import com.rafcode.schedulefootball.utils.TempData
import com.rafcode.schedulefootball.utils.ViewPagerAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.content_detail_team.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout



class DetailTeamActivity : AppCompatActivity() {

    lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        initToolbar()

        team = TempData.teams!!

        Picasso.with(this).load(team.strTeamBadge).into(ivTeam)

        tvTeam.text = team.strTeam
        tvYear.text = team.intFormedYear
        tvStadion.text = team.strStadium

        setupViewPager()
        tlTeam.setupWithViewPager(vpTeam)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = TempData.teams?.strTeam
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TeamOverviewFragment().getInstance(), "Overview")
        adapter.addFragment(TeamListPlayerFragment().getInstance(), "Player")
        vpTeam.adapter = adapter
        vpTeam.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val fragment = adapter.instantiateItem(vpTeam, position) as Fragment
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)

        database.use {
            select(FavoriteTeam.TABLE_TEAM)
                .whereArgs("${FavoriteTeam.idTeam} = ${TempData.teams?.idTeam}").exec {
                    if (count == 0) {
                        menu?.getItem(0)?.icon = resources.getDrawable(R.drawable.ic_star_border_black_24dp)
                    } else {
                        menu?.getItem(0)?.icon = resources.getDrawable(R.drawable.ic_star_black_24dp)

                    }

                }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_fav) {
            val team = TempData.teams ?: return true
            database.use {
                select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("${FavoriteTeam.idTeam} = ${team.idTeam}").exec {
                        if (count == 0) {
                            insert(
                                FavoriteTeam.TABLE_TEAM,
                                FavoriteTeam.idTeam to team.idTeam,
                                FavoriteTeam.gson to Gson().toJson(team)
                            )

                            Snackbar.make(findViewById(android.R.id.content), "Berhasil menambahkan team favorit", 2000).show()
                            item.icon = resources.getDrawable(R.drawable.ic_star_black_24dp)
                        } else {
                            delete(FavoriteTeam.TABLE_TEAM, FavoriteTeam.idTeam + " = " + team.idTeam)
                            Snackbar.make(findViewById(android.R.id.content), "Berhasil hapus team favorit", 2000).show()
                            item.icon = resources.getDrawable(R.drawable.ic_star_border_black_24dp)
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
