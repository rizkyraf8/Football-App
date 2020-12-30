package com.rafcode.schedulefootball.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

@SuppressLint("StaticFieldLeak")
class PrefHelper(private val mContext: Context) {

    private val APP_PREFS = "application_preferences"

    private var preferences: SharedPreferences? = null

    private fun initPref() {
        preferences = mContext.getSharedPreferences(
            APP_PREFS,
            Context.MODE_PRIVATE
        )
    }

    fun setString(key: PrefKey, value: String) {
        initPref()
        val editor = preferences!!.edit()
        editor.putString(key.toString(), value)
        editor.apply()
    }

    fun getString(key: PrefKey): String? {
        initPref()
        return preferences!!.getString(key.toString(), "")
    }

    fun setInt(key: PrefKey, value: Int) {
        initPref()
        val editor = preferences!!.edit()
        editor.putInt(key.toString(), value)
        editor.apply()
    }

    fun getInt(key: PrefKey): Int {
        initPref()
        return preferences!!.getInt(key.toString(), -1)
    }

    fun setBoolean(key: PrefKey, value: Boolean) {
        initPref()
        val editor = preferences!!.edit()
        editor.putBoolean(key.toString(), value)
        editor.apply()
    }

    fun getBoolean(key: PrefKey): Boolean {
        initPref()
        return preferences!!.getBoolean(key.toString(), false)
    }

    fun setObject(key: PrefKey, `object`: Any) {
        val jsonObject = Gson().toJson(`object`)
        setString(key, jsonObject)
    }

    fun getObject(key: PrefKey, clazz: Class<*>): Any? {
        return if (getString(key)!!.isEmpty()) null else Gson().fromJson(
            getString(key),
            clazz
        )
    }

    fun clearPreference(key: PrefKey) {
        initPref()
        val editor = preferences!!.edit()
        editor.remove(key.toString())
        editor.apply()
    }

    fun clearAllPreferences() {
        initPref()
        val editor = preferences!!.edit()
        editor.clear()
        editor.apply()
    }

    enum class PrefKey {
        BOOLEAN_IS_LOGIN,
        OBJECT_USER,
    }

}
