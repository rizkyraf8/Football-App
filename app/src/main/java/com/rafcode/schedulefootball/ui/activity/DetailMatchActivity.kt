package com.rafcode.schedulefootball.ui.activity

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.ApiService
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.databinding.ActivityDetailMatchBinding
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat


class DetailMatchActivity : BaseActivity<ActivityDetailMatchBinding>() {

    private val event: Event? by lazy {
        intent.getParcelableExtra<Event>("event")
    }

    override fun getViewBinding(): ActivityDetailMatchBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_detail_match)
    }

    override fun onActivityCreated() {
        initToolbar()
    }

    override fun onActivityClick() {

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        super.onResume()
        if (event?.idEvent != null) {

            event?.strHomeGoalDetails = event?.strHomeGoalDetails.toString().replace(";", "\n")
            event?.strAwayLineupGoalkeeper =
                event?.strAwayLineupGoalkeeper.toString().replace(";", "\n")

            event?.strHomeLineupDefense = event?.strHomeLineupDefense.toString().replace(";", "\n")
            event?.strAwayLineupDefense = event?.strAwayLineupDefense.toString().replace(";", "\n")

            event?.strHomeLineupMidfield =
                event?.strHomeLineupMidfield.toString().replace(";", "\n")
            event?.strAwayLineupMidfield =
                event?.strAwayLineupMidfield.toString().replace(";", "\n")

            event?.strHomeLineupForward = event?.strHomeLineupForward.toString().replace(";", "\n")
            event?.strAwayLineupForward = event?.strAwayLineupForward.toString().replace(";", "\n")

            event?.strHomeLineupSubstitutes =
                event?.strHomeLineupSubstitutes.toString().replace(";", "\n")
            event?.strAwayLineupSubstitutes =
                event?.strAwayLineupSubstitutes.toString().replace(";", "\n")

            binding.layout.event = event

            val jam = event?.strTime?.split("+")

            val dateString = event?.dateEvent + " " + (jam?.get(0)?.substring(0, 5) ?: "00:00")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val convertedDate = dateFormat.parse(dateString)

            convertedDate?.time?.let {
                binding.layout.tvEventDate.text = formatDate(it)
                binding.layout.tvJam.text = formatJam(it)
            }
        }
        getPhoto("home", event?.idHomeTeam)
    }

    @SuppressLint("CheckResult")
    fun getPhoto(type: String, id: String?) {
        try {
            val service: ApiService.Api = ApiService().getService()

            service.detailTeam(getUser().token.toString(), id.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        result.teams?.forEach { data ->
                            run {
                                if (type == "home") {
                                    Picasso.with(this).load(data.strTeamBadge)
                                        .into(binding.layout.ivHome)
                                    getPhoto("away", event?.idAwayTeam)
                                } else {
                                    Picasso.with(this).load(data.strTeamBadge)
                                        .into(binding.layout.ivAway)
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
                .whereArgs("${FavoriteMatch.idEvent} = ${event?.idEvent}").exec {
                    if (count == 0) {
                        menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                            activity,
                            R.drawable.ic_star_border_black_24dp
                        )
                    } else {
                        menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                            activity,
                            R.drawable.ic_star_black_24dp
                        )
                    }
                }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_fav) {
            database.use {
                select(FavoriteMatch.TABLE_MATCH)
                    .whereArgs("${FavoriteMatch.idEvent} = ${event?.idEvent}").exec {
                        if (count == 0) {
                            insert(
                                FavoriteMatch.TABLE_MATCH,
                                FavoriteMatch.idEvent to event?.idEvent,
                                FavoriteMatch.gson to Gson().toJson(event)
                            )

                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Berhasil menambahkan match favorit",
                                2000
                            ).show()
                            item.icon =
                                ContextCompat.getDrawable(activity, R.drawable.ic_star_black_24dp)
                        } else {
                            delete(
                                FavoriteMatch.TABLE_MATCH,
                                FavoriteMatch.idEvent + " = " + event?.idEvent
                            )
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Berhasil hapus match favorit",
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
