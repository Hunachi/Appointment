package io.github.hunachi.appointment.infra.mail

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.github.hunachi.appointment.data.Professor
import io.github.hunachi.appointment.data.SentMailData
import io.github.hunachi.appointment.infra.data.SendEmailData
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

class MailRepository {

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val requestAdapter = moshi.adapter(SendEmailData::class.java)
    val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")

    suspend fun sendMail(mailData: SendEmailData) {
        val result = Fuel.post("https://~~.amazonaws.com/v1/mail")
            .header(header)
            .body(requestAdapter.toJson(mailData))
            .responseString()
    }
}