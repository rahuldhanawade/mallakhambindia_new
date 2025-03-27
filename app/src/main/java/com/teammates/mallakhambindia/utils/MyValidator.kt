package com.teammates.mallakhambindia.utils

import android.text.TextUtils
import android.util.Patterns
import java.text.ParseException
import java.util.regex.Pattern

object MyValidator {
    private const val EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private const val AADHAR_REGEX = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$"
    private const val REQUIRED_MSG = "Field required"
    private const val GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}"
    private const val GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

//    fun isValidEmailOrMobile(emailMobile: EditText): Boolean {
//        val str = emailMobile.text.toString()
//        return when {
//            str.isEmpty() -> false
//            isEmailValid(str) || isValidPhone(str) -> true
//            str.length == 10 && Pattern.compile("([6-9][0-9]{9})").matcher(str).matches() -> true
//            else -> false
//        }
//    }

    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPhone(phone: CharSequence) = !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches()

    fun isValidField(strField: String): Boolean {
        val value = strField.trim()
        return if (value.isEmpty()) {
            false
        } else {
            true
        }
    }

//    fun isValidPincode(editText: EditText): Boolean {
//        val value = editText.text.toString().trim()
//        return if (value.length != 6) {
//            editText.error = "Please Enter Valid Pincode"
//            false
//        } else {
//            editText.error = null
//            true
//        }
//    }
//
//    fun isValidAge(editText: EditText): Boolean {
//        val value = editText.text.toString().trim()
//        return when {
//            value.isEmpty() -> {
//                editText.error = REQUIRED_MSG
//                false
//            }
//            value.length > 2 || value.toIntOrNull() == 0 -> {
//                editText.error = "Age must be between 1 and 99"
//                false
//            }
//            else -> {
//                editText.error = null
//                true
//            }
//        }
//    }
//
//    fun isValidSpinner(spinner: Spinner) = spinner.selectedItemPosition != 0
//    fun isValidRadioGroup(radioGroup: RadioGroup) = radioGroup.checkedRadioButtonId != -1
//    fun isValidCheckBox(checkBox: CheckBox) = checkBox.isChecked
//
//    fun isValidMobile(editText: EditText): Boolean {
//        val mob = editText.text.toString().trim()
//        return if (mob.length != 10 || !Pattern.compile("([6-9][0-9]{9})").matcher(mob).matches()) {
//            editText.error = "Invalid Mobile No. Enter Valid Mobile Number"
//            false
//        } else {
//            editText.error = null
//            true
//        }
//    }
//
//    fun checkDates(startDT: String, endDT: String, edtEndDate: EditText): Boolean {
//        val df = SimpleDateFormat("yyyy-MM-dd")
//        return try {
//            when {
//                df.parse(startDT).before(df.parse(endDT)) -> true
//                else -> {
//                    edtEndDate.error = "End Date Should Be Greater Than Start Date"
//                    false
//                }
//            }
//        } catch (e: ParseException) {
//            e.printStackTrace()
//            false
//        }
//    }
}
