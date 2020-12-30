package com.rafcode.schedulefootball

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.rafcode.schedulefootball.api.response.LeagueResponse
import com.rafcode.schedulefootball.databinding.ActivityMainBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.LeagueView
import com.rafcode.schedulefootball.ui.activity.BaseActivity
import com.rafcode.schedulefootball.ui.activity.LoginActivity
import com.rafcode.schedulefootball.ui.activity.SearchMatchActivity
import com.rafcode.schedulefootball.ui.activity.SearchTeamActivity
import com.rafcode.schedulefootball.ui.fragment.FavoriteFragment
import com.rafcode.schedulefootball.ui.fragment.MatchFragment
import com.rafcode.schedulefootball.ui.fragment.TeamFragment
import com.rafcode.schedulefootball.ui.presenter.LeaguePresenter
import com.rafcode.schedulefootball.utils.NavigationBottom
import com.rafcode.schedulefootball.utils.PrefHelper
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navigationBottom: NavigationBottom by lazy {
        NavigationBottom(this, binding.layout.bnMenu)
    }
    private var menu: Menu? = null
    private var postionMenu = 0

    override fun getViewBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onActivityCreated() {
        initToolbar()

        initNavigationBottom()

        database.use {
            val result = select(LeagueDatabase.TABLE_LEAGUE)
            val league = result.parseList(classParser<LeagueDatabase>())

            if (league.isEmpty()) {
                initLeague()
            } else {
                displayFragment(0)
            }
        }
    }

    override fun onActivityClick() {

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initNavigationBottom() {
        navigationBottom.setBottomNavigation(AHBottomNavigation.OnTabSelectedListener { position, _ ->
            displayFragment(position)
            false
        }, 0)
    }

    private val fragment = listOf(
        MatchFragment.Companion.newInstance(),
        TeamFragment.Companion.newInstance(),
        FavoriteFragment.Companion.newInstance()

    )

    fun displayFragment(position: Int) {
        navigationBottom.bottomNavigation.setCurrentItem(position, false)

        supportFragmentManager.beginTransaction().apply {
            if (fragment[position].isAdded) {
                show(fragment[position])
            } else {
                add(R.id.fragment, fragment[position], fragment[position].tag)
            }

            supportFragmentManager.fragments.forEach {
                if (it != fragment[position] && it.isAdded) {
                    hide(it)
                }
            }

            when (position) {
                0 -> {
                    postionMenu = 0
                    menu?.getItem(0)?.isVisible = true
                    menu?.getItem(1)?.isVisible = false
                }
                1 -> {
                    postionMenu = 1
                    menu?.getItem(0)?.isVisible = true
                    menu?.getItem(1)?.isVisible = false
                }
                2 -> {
                    menu?.getItem(0)?.isVisible = false
                    menu?.getItem(1)?.isVisible = true
                }
            }
        }.commit()

    }

    private fun initLeague() {
        val leaguePresenter = LeaguePresenter(object : LeagueView {
            override fun onShowLoadingLeague() {
                getProgressDialog("Mempersiapkan data...")
            }

            override fun onHideLoadingLeague() {
            }

            override fun onDataLoaded(data: LeagueResponse?) {
                data?.leagues?.forEach { item ->
                    database.use {
                        insert(
                            LeagueDatabase.TABLE_LEAGUE,
                            LeagueDatabase.idLeague to item.idLeague,
                            LeagueDatabase.strLeague to item.strLeague + "",
                            LeagueDatabase.strSport to item.strSport + "",
                            LeagueDatabase.strLeagueAlternate to item.strLeagueAlternate + ""
                        )
                    }
                }
                displayFragment(0)
                dismisProgressDialog()
            }

            override fun onDataError() {
            }
        }, ApiRepository())
        leaguePresenter.getAllLeague(getUser().token.toString())

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                MaterialDialog(this).show {
                    title(null, "Konfirmasi")
                    message(null, "Kamu yakin ingin keluar?")
                    positiveButton(null, "Yakin") { dialog ->
                        val prefHelper = PrefHelper(activity)
                        prefHelper.clearPreference(PrefHelper.PrefKey.BOOLEAN_IS_LOGIN)
                        prefHelper.clearPreference(PrefHelper.PrefKey.OBJECT_USER)
                        startActivity(Intent(activity, LoginActivity::class.java))
                        finish()
                        dialog.dismiss()
                    }
                    negativeButton(null, "Batal") { dialog ->
                        dialog.dismiss()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
