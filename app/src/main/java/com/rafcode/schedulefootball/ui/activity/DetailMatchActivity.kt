package com.rafcode.schedulefootball.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.ApiService
import com.rafcode.schedulefootball.utils.TempData
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.content_detail_match.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*


class DetailMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        initToolbar()

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        super.onResume()
        if (TempData.event != null) {
            val event = TempData.event
            tvHomeName.text = event!!.strHomeTeam
            tvAwayName.text = event.strAwayTeam

            val jam = event.strTime?.split("+")

            val dateString = event.dateEvent + " " + (jam?.get(0)?.substring(0,5) ?: "00:00")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val convertedDate = dateFormat.parse(dateString)

            tvEventDate.text = formatDate(convertedDate.time)
            tvJam.text = formatJam(convertedDate.time)

            if (event.intHomeScore != null) {

                tvHomeGoals.text = event.intHomeScore.toString()
                tvAwayGoals.text = event.intAwayScore.toString()

                tvHomeShots.text = event.intHomeShots.toString()
                tvAwayShots.text = event.intAwayShots.toString()

                tvHomeGoalKeeper.text = event.strHomeGoalDetails.toString().replace(";", "\n")
                tvAwayGoalKeeper.text = event.strAwayLineupGoalkeeper.toString().replace(";", "\n")

                tvHomeDefense.text = event.strHomeLineupDefense.toString().replace(";", "\n")
                tvAwayDefense.text = event.strAwayLineupDefense.toString().replace(";", "\n")

                tvHomeMidfield.text = event.strHomeLineupMidfield.toString().replace(";", "\n")
                tvAwayMidfield.text = event.strAwayLineupMidfield.toString().replace(";", "\n")

                tvHomeForward.text = event.strHomeLineupForward.toString().replace(";", "\n")
                tvAwayForward.text = event.strAwayLineupForward.toString().replace(";", "\n")

                tvHomeSubsitutes.text = event.strHomeLineupSubstitutes.toString().replace(";", "\n")
                tvAwaySubsitutes.text = event.strAwayLineupSubstitutes.toString().replace(";", "\n")

            }
        }
        getPhoto("home", Integer.parseInt(TempData.event?.idHomeTeam))
    }

    fun formatDate(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("EEE, dd MMM YYYY", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    fun formatJam(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    fun getPhoto(type: String, id: Int) {
        val any = try {
            val service: ApiService.Api = ApiService().getService()

            service.detailTeam(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                result.teams?.forEach { data ->
                                    run {
                                        if (type == "home") {
                                            Picasso.with(this).load(data.strTeamBadge).into(ivHome)
                                            getPhoto("away", Integer.parseInt(TempData.event?.idAwayTeam))
                                        } else {
                                            Picasso.with(this).load(data.strTeamBadge).into(ivAway)
                                        }
                                    }
                                }
                            },
                            { error ->
                                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            }
                    )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)

        database.use {
            select(FavoriteMatch.TABLE_MATCH)
                    .whereArgs("${FavoriteMatch.idEvent} = ${TempData.event?.idEvent}").exec {
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
            val event = TempData.event ?: return true
            database.use {
                select(FavoriteMatch.TABLE_MATCH)
                        .whereArgs("${FavoriteMatch.idEvent} = ${event.idEvent}").exec {
                            if (count == 0) {
                                insert(
                                        FavoriteMatch.TABLE_MATCH,
                                        FavoriteMatch.idEvent to event.idEvent,
                                        FavoriteMatch.gson to Gson().toJson(event)
                                )

                                Snackbar.make(findViewById(android.R.id.content), "Berhasil menambahkan match favorit", 2000).show()
                                item.icon = resources.getDrawable(R.drawable.ic_star_black_24dp)
                            } else {
                                delete(FavoriteMatch.TABLE_MATCH, FavoriteMatch.idEvent + " = " + event.idEvent)
                                Snackbar.make(findViewById(android.R.id.content), "Berhasil hapus match favorit", 2000).show()
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
