package com.rafcode.schedulefootball.ui.activity

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.request.AuthRequest
import com.rafcode.schedulefootball.api.response.AuthResponse
import com.rafcode.schedulefootball.databinding.ActivityRegisterBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.AuthView
import com.rafcode.schedulefootball.ui.presenter.AuthPresenter

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    lateinit var authPresenter: AuthPresenter

    override fun getViewBinding(): ActivityRegisterBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_register)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Daftar Akun"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityCreated() {
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

                    Toast.makeText(
                        activity,
                        "Berhasil mendaftarkan Akun Kamu",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                } else {
                    Toast.makeText(
                        activity,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onDataError() {

            }
        }, ApiRepository())

    }

    override fun onActivityClick() {
        binding.layout.bRegister.setOnClickListener {
            getRegister()
        }
    }

    fun getRegister() {

        val params = AuthRequest()
        params.password = binding.layout.etPassword.text.toString()
        params.email = binding.layout.etEmail.text.toString()
        params.name = binding.layout.etName.text.toString()

        if (!isValidEmail(params.email.toString())) {
            Toast.makeText(
                activity,
                "Email tidak valid",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        authPresenter.getAuthRegister(params)
    }
}