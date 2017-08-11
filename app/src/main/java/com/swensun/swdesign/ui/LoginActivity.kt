package com.swensun.swdesign.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.swensun.swdesign.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_in_btn.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {
        account_input_layout.error = null
        password_input_layout.error = null

        val accountString = account.text.toString()
        val passwordString = password.text.toString()

        var cancel = false
        var focusView: View? = null


        if (TextUtils.isEmpty(accountString)) {
            account_input_layout.error = getString(R.string.error_field_required)
            focusView = account
            cancel = true
        } else if (!isEmailValid(accountString)) {
            account_input_layout.error = getString(R.string.error_invalid_email)
            focusView = account
            cancel = true
        } else if (!TextUtils.isEmpty(accountString) && !isPasswordValid(passwordString)) {
            password_input_layout.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }
        if (cancel) {
            focusView?.requestFocus()
        } else {
            goLogin(accountString, passwordString)
        }
    }

    private fun goLogin(accountString: String, passwordString: String) {
        //simulate login
        Observable.timer(2, TimeUnit.SECONDS)
                .doOnSubscribe {
                    login_form.visibility = View.GONE
                    login_progress.visibility = View.VISIBLE
                }.subscribe {
            finish()
        }
    }

    private fun isEmailValid(account: String): Boolean {
        return account.contains("@") || account.matches(Regex("^1\\d{10}$"))
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
}

