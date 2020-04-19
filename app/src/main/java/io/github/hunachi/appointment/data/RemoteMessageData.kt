package io.github.hunachi.appointment.data

data class RemoteMessageData(
    val GCM: GCM
)

data class GCM(
    val notification: NotificationData
)

data class NotificationData(
    val text: TextRemote
)

data class TextRemote(
    val accepted: Boolean
)