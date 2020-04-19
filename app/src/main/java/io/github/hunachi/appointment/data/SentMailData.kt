package io.github.hunachi.appointment.data

data class SentMailData(
    val to_addr: String,
    val email_body: String,
    val email: String,
    val subject: String
)