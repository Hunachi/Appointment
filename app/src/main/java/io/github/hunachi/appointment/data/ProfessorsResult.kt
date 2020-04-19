package io.github.hunachi.appointment.data


data class ProfessorsResult(
    val statusCode: Int,
    val body: List<Body>
)

data class Body(
    val lecture: String,
    val nickname: String,
    val email: String,
    val name: String,
    val image: String
)