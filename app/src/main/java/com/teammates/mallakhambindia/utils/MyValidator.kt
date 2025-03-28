package com.teammates.mallakhambindia.utils

import android.text.TextUtils
import android.util.Patterns
import java.text.ParseException
import java.util.regex.Pattern

object MyValidator {

    private val emailPattern: Pattern = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    )


    fun isValidEmail(value: String): Boolean {
        val trimmedValue = value.trim()

        return emailPattern.matcher(trimmedValue).matches()
    }

    fun isValidPassword(value: String): Boolean {
        val trimmedValue = value.length

        return trimmedValue in 8..15
    }

}
