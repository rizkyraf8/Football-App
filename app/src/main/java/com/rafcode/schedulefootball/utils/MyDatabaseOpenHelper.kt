package com.rafcode.schedulefootball.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import com.rafcode.schedulefootball.utils.database.FavoriteTeam
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoriteMatch.TABLE_MATCH, true,
            FavoriteMatch.idEvent to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.gson to TEXT
        )
        db.createTable(
            FavoriteTeam.TABLE_TEAM, true,
            FavoriteTeam.idTeam to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.gson to TEXT
        )
        db.createTable(
            LeagueDatabase.TABLE_LEAGUE, true,
            LeagueDatabase.idLeague to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            LeagueDatabase.strLeague to TEXT,
            LeagueDatabase.strSport to TEXT,
            LeagueDatabase.strLeagueAlternate to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_MATCH, true)
        db.dropTable(FavoriteTeam.TABLE_TEAM, true)
        db.dropTable(LeagueDatabase.TABLE_LEAGUE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)