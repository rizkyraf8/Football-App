package com.rafcode.schedulefootball.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.api.response.UserBeans
import com.rafcode.schedulefootball.utils.PrefHelper
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    val binding: B by lazy {
        getViewBinding()
    }

    abstract fun getViewBinding(): B

    val activity: BaseActivity<B>
        get() = this

    abstract fun onActivityCreated()
    abstract fun onActivityClick()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onActivityCreated()
        onActivityClick()
    }

    fun getUser(): UserBeans {
        return try {
            PrefHelper(activity).getObject(
                PrefHelper.PrefKey.OBJECT_USER,
                UserBeans::class.java
            ) as UserBeans
        } catch (e: Exception) {
            UserBeans()
        }
    }

    val progressDialog: MaterialDialog by lazy {
        MaterialDialog(activity).customView(R.layout.dialog_progress).cancelable(false)
    }

    fun getProgressDialog(title: String) {
        progressDialog.findViewById<TextView>(R.id.tvLoading).text = title
        progressDialog.show()
    }

    fun dismisProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }

    fun formatDate(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("EEE, dd MMM YYYY", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    fun formatJam(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.US)
        return simpleDateFormat.format(Date(date))
    }

    open fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun addToCalendar(data: Event, convertedDate: Date) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertedDate.time)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, convertedDate.time)
            .putExtra(CalendarContract.Events.TITLE, data.strHomeTeam + " VS " + data.strAwayTeam)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, data.strCountry.toString())
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        startActivity(intent)
    }
}