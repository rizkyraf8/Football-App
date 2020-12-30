package com.rafcode.schedulefootball.ui.activity

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.rafcode.schedulefootball.MainActivity
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.request.AuthRequest
import com.rafcode.schedulefootball.api.response.AuthResponse
import com.rafcode.schedulefootball.databinding.ActivityLoginBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.AuthView
import com.rafcode.schedulefootball.ui.presenter.AuthPresenter
import com.rafcode.schedulefootball.utils.PrefHelper

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    lateinit var authPresenter: AuthPresenter

    override fun getViewBinding(): ActivityLoginBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun onActivityCreated() {
        if (PrefHelper(activity).getBoolean(PrefHelper.PrefKey.BOOLEAN_IS_LOGIN)) {
            startActivity(Intent(activity, MainActivity::class.java))
            finish()
            return
        }

        initToolbar()

        authPresenter = AuthPresenter(object : AuthView {
            override fun onShowLoadingAuth() {
                getProgressDialog("Sedang mengambil data kamu...")
            }

            override fun onHideLoadingAuth() {
                dismisProgressDialog()
            }

            override fun onDataLoaded(response: AuthResponse?) {
                if (!response?.error!!) {

                    response.data?.let { data ->
                        val prefHelper = PrefHelper(activity)

                        prefHelper.setBoolean(PrefHelper.PrefKey.BOOLEAN_IS_LOGIN, true)

                        data.token = data.type.toString() + " " + data.token.toString()
                        prefHelper.setObject(
                            PrefHelper.PrefKey.OBJECT_USER,
                            data
                        )

                        startActivity(Intent(activity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Username dan Passsword tidak sesuai",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onDataError() {

            }
        }, ApiRepository())
    }

    override fun onActivityClick() {
        binding.layout.bLogin.setOnClickListener {
            getLogin()
        }

        binding.layout.llDaftar.setOnClickListener {
            startActivity(Intent(activity, RegisterActivity::class.java))
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Masuk"
        binding.appBar.visibility = View.GONE
    }

    fun getLogin() {

        val params = AuthRequest()
        params.password = binding.layout.etPassword.text.toString()
        params.email = binding.layout.etEmail.text.toString()

        if (!isValidEmail(params.email.toString())) {
            Toast.makeText(
                activity,
                "Email tidak valid",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        authPresenter.getAuthLogin(params)
    }
}