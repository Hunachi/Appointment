package io.github.hunachi.appointment.infra.professors

import io.github.hunachi.appointment.data.Body
import io.github.hunachi.appointment.data.Professor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfessorsUseCase {

    private val repository = ProfessorsRepository()

    suspend fun professors(key: String, word: String) = withContext(Dispatchers.IO) {
        val list = mutableListOf<Body>()
        when (key) {
            "name" -> list.addAll(repository.professorByName(word))
            "nickname" -> list.addAll(repository.professorByNickname(word))
            "lecture" -> list.addAll(repository.professorByLecture(word))
            else -> list.addAll(repository.professors())
        }
        return@withContext list.map { Professor(it.name, it.lecture, it.nickname, it.email, it.image) }
    }
}