package com.rafcode.schedulefootball.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Utils{

    @SuppressLint("SimpleDateFormat")
    fun toSimpleString(date: Date?): String? = with(date ?: Date()) {
        SimpleDateFormat("EEE, dd MM yyy").format(this)
    }

}
