package com.teammates.mallakhambindia.utils

import android.text.TextUtils
import android.util.Patterns
import java.text.ParseException
import java.util.regex.Pattern

object MyValidator {


    fun isValidField(value: String): Boolean {
        return value.isNotEmpty() && value.length >= 4
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

}
