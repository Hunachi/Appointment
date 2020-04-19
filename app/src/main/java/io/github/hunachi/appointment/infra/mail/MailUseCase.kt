package io.github.hunachi.appointment.infra.mail

import io.github.hunachi.appointment.data.MailData
import io.github.hunachi.appointment.data.Professor
import io.github.hunachi.appointment.data.User
import io.github.hunachi.appointment.infra.data.SendEmailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MailUseCase {

    private val repository = MailRepository()

    suspend fun sendMail(
        professorEmail: String, emailBody: String
        , user: User, subject: String
    ) = withContext(Dispatchers.IO) {
        return@withContext repository.sendMail(
            SendEmailData(
                toAddress = professorEmail,
                emailBody = emailBody,
                fromAddress = user.mail,
                subject = subject
            )
        )
    }
}