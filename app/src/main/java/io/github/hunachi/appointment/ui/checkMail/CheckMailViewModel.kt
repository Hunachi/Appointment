package io.github.hunachi.appointment.ui.checkMail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.hunachi.appointment.data.MailData
import io.github.hunachi.appointment.data.User
import io.github.hunachi.appointment.infra.mail.MailUseCase
import io.github.hunachi.appointment.launchDataLoad
import java.util.*

class CheckMailViewModel : ViewModel() {

    private val mailUseCase = MailUseCase()

    private val _successMail = MutableLiveData<Boolean>()
    val successMail: LiveData<Boolean> = _successMail

    private val _mailBody = MutableLiveData<String>()
    val mailBody: LiveData<String> = _mailBody

    private val _mailData = MutableLiveData<MailData>()
    val mailData: LiveData<MailData> = _mailData


    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _nowEdit = MutableLiveData<Boolean>()
    val nowEdit: LiveData<Boolean> = _nowEdit

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner

    fun sendMail() {
        try {
            launchDataLoad(_spinner) {
                mailUseCase.sendMail(
                    mailData.value!!.professor.email,
                    mailBody.value!!,
                    user.value!!,
                    mailData.value?.reason!!
                )
                _successMail.value = true
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    fun createMailBody(mail: MailData, user: User) {
        _user.value = user
        _mailData.value = mail
        _mailBody.value = "${mail.professor.name} 先生" + "\n\n" + nowAggrement() + "" +
                "${mail.date.month}月${mail.date.date}日${mail.date.hour}時" +
                "${mail.date.minute}分に${mail.reason}について" +
                "お話ししたい為、${mail.place}でお会いしたいです。" +
                "\nよろしくお願い致します。" +
                "\n\n${user.name}"
    }

    fun changeEditState() {
        _nowEdit.value = !(nowEdit.value ?: false)
    }

    private fun nowAggrement(): String {
        val now = Calendar.getInstance()
        return when (now.get(Calendar.HOUR_OF_DAY)) {
            in 4..9 -> "おはようございます。\n"
            in 10..16 -> "こんにちは。\n"
            else -> "こんばんは。\n"
        }
    }
}
