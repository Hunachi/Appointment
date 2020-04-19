package io.github.hunachi.appointment.infra.data

import com.google.gson.annotations.SerializedName

data class SendEmailData(

    @SerializedName("to_addr")
    val toAddress: String,
    @SerializedName("email_body")
    val emailBody: String,
    @SerializedName("from_addr")
    val fromAddress: String,
    val subject: String
)