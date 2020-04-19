package io.github.hunachi.appointment.data

import java.io.Serializable

data class Professor(
    var name: String = "教授",
    val lecture: String,
    val nickname: String,
    val email: String,
    val imageUrl: String = ""
): Serializable