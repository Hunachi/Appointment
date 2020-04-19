package io.github.hunachi.appointment

import android.content.Context
import androidx.core.content.edit
import io.github.hunachi.appointment.data.User

class MyPreference(context: Context) {

    private val preference = context.getSharedPreferences(KEY_APPLICATION, Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        if (user.name.isNotEmpty()) markUserName(user.name)
        if (user.department.isNotEmpty()) markUserDeepartment(user.department)
        if (user.number.isNotEmpty()) markUserNumber(user.number)
        if (user.mail.isNotEmpty()) markUserMail(user.mail)
    }

    fun user() = User(
        name = name(),
        department = department(),
        mail = mail(),
        number = number()
    )

    private fun markUserName(name: String) = preference.edit { putString(KEY_USER_NAME, name) }

    private fun name() = preference.getString(KEY_USER_NAME, "") ?: ""

    private fun markUserMail(name: String) = preference.edit { putString(KEY_USER_MAIL, name) }

    private fun mail() = preference.getString(KEY_USER_MAIL, "") ?: ""

    private fun markUserDeepartment(name: String) =
        preference.edit { putString(KEY_USER_DEPARTMENT, name) }

    private fun department() = preference.getString(KEY_USER_DEPARTMENT, "") ?: ""

    private fun markUserNumber(name: String) = preference.edit { putString(KEY_USER_NUMBER, name) }

    private fun number() = preference.getString(KEY_USER_NUMBER, "") ?: ""

    fun markEndpointArn(endpointArn: String) =
        preference.edit { putString(KAY_ENDPOINT_ARN, endpointArn) }

    fun endpointArn() = preference.getString(KAY_ENDPOINT_ARN, "") ?: ""

    companion object {
        private const val KEY_APPLICATION = "appointment-to-professor"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_MAIL = "mail"
        private const val KEY_USER_DEPARTMENT = "department"
        private const val KEY_USER_NUMBER = "number"
        private const val KAY_ENDPOINT_ARN = "endpoint_arn"
    }
}