package io.github.hunachi.appointment.data

data class CalendarData(
    var year: Int = 0,
    var month: Int = 0,
    var date: Int = 0,
    var hour: Int = 0,
    var minute: Int = 0
){
    fun convertToString() = "$month/$date/$hour:$minute"
}