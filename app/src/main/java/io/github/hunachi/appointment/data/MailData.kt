package io.github.hunachi.appointment.data

import java.io.Serializable

data class MailData(
    val professor: Professor,
    val date: CalendarData,
    val place: String,
    val reason: String
): Serializable