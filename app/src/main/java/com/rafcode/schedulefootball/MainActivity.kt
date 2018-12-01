package com.rafcode.schedulefootball

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.rafcode.schedulefootball.api.response.Leagues
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.activity.SearchMatchActivity
import com.rafcode.schedulefootball.ui.activity.SearchTeamActivity
import com.rafcode.schedulefootball.ui.fragment.FavoriteFragment
import com.rafcode.schedulefootball.ui.fragment.MatchFragment
import com.rafcode.schedulefootball.ui.fragment.TeamFragment
import com.rafcode.schedulefootball.ui.presenter.LeaguePresenter
import com.rafcode.schedulefootball.ui.view.LeagueView
import com.rafcode.schedulefootball.utils.NavigationBottom
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class MainActivity : AppCompatActivity() {

    lateinit var navigationBottom: NavigationBottom
    lateinit var menu: Menu
    var postionMenu = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        initNavigationBottom(0)

        database.use {
            val result = select(LeagueDatabase.TABLE_LEAGUE)
            val league = result.parseList(classParser<LeagueDatabase>())

            if (league.isEmpty()) {
                initLeague()
            } else {
                setFragment(0, 0)
            }
        }
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    fun initNavigationBottom(selected: Int) {
        navigationBottom = NavigationBottom(this, bnMenu)
        navigationBottom.setBottomNavigation(AHBottomNavigation.OnTabSelectedListener { position, _ ->
            when (position) {
                1 -> {
                    setFragment(1, 1)
                }
                2 -> {
                    setFragment(2, 2)
                }
                else -> {
                    setFragment(0, 0)
                }
            }
            false
        }, selected)
    }

    fun setFragment(pos: Int, selected: Int) {
        try {
            navigationBottom.bottomNavigation.setCurrentItem(selected, false)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, getFragment(pos))
                    .commit()

            when (pos) {
                0 -> {
                    postionMenu = 0
                    menu.getItem(0).isVisible = true
                }
                1 -> {
                    postionMenu = 1
                    menu.getItem(0).isVisible = true
                }
                2 -> {
                    menu.getItem(0).isVisible = false
                }
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    fun getFragment(pos: Int): Fragment {
        var fragment: Fragment? = null

        when (pos) {
            0 -> {
                fragment = MatchFragment().newInstance()
            }
            1 -> {
                fragment = TeamFragment().newInstance()
            }
            2 -> {
                fragment = FavoriteFragment().newInstance()
            }
        }

        return fragment!!
    }

    private fun initLeague() {
        val leaguePresenter = LeaguePresenter(object : LeagueView {
            override fun onShowLoadingLeague() {

            }

            override fun onHideLoadingLeague() {

            }

            override fun onDataLoaded(data: Leagues?) {
                data?.leagues?.forEach { data ->
                    if (data.strSport.equals("Soccer")) {
                        database.use {
                            insert(
                                    LeagueDatabase.TABLE_LEAGUE,
                                    LeagueDatabase.idLeague to data.idLeague,
                                    LeagueDatabase.strLeague to data.strLeague + "",
                                    LeagueDatabase.strSport to data.strSport + "",
                                    LeagueDatabase.strLeagueAlternate to data.strLeagueAlternate + ""
                            )
                        }
                    }
                }

                setFragment(0, 0)

            }

            override fun onDataError() {
            }
        }, ApiRepository())
        leaguePresenter.getAllLeague()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        this.menu = menu
        val sManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val sView = menu.findItem(R.id.menu_se).actionView as SearchView
        sView.setSearchableInfo(sManager.getSearchableInfo(componentName))
        sView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                val intent: Intent = when (postionMenu) {
                    1 -> {
                        Intent(this@MainActivity, SearchTeamActivity::class.java)
                    }
                    else -> {
                        Intent(this@MainActivity, SearchMatchActivity::class.java)
                    }
                }
                intent.putExtra("key", query)
                startActivity(intent)
                sView.setQuery("", false)

                menu.getItem(0).collapseActionView()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {

                return true
            }
        })

        return true
    }
}
