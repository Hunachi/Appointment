package io.github.hunachi.appointment.infra.professors

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.github.hunachi.appointment.data.Body
import io.github.hunachi.appointment.data.Professor
import io.github.hunachi.appointment.data.ProfessorsResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfessorsRepository {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    suspend fun professors() = suspendCoroutine<List<Body>> { co ->
        "https://~~.amazonaws.com/v1/professors".httpGet()
            .responseObject<ProfessorsResult> { req, res, result ->
                val (professorResult, err) = result
                err?.let { it1 ->
                    co.resumeWithException(it1)
                }
                professorResult?.body?.let {
                    co.resume(it)
                }
            }
    }

    suspend fun professorByName(word: String) = suspendCoroutine<List<Body>> { co ->
        "https://~~.amazonaws.com/v1/professors".httpGet(
            listOf("key" to "name", "name" to word)
        ).responseObject<ProfessorsResult> { req, res, result ->
            val (professorResult, err) = result
            professorResult?.body?.let {
                co.resume(it)
            } ?: co.resumeWithException(err!!)
        }
    }

    suspend fun professorByLecture(word: String) = suspendCoroutine<List<Body>> { co ->
        "https://~~.amazonaws.com/v1/professors".httpGet(
            listOf("key" to "lecture", "lecture" to word)
        ).responseObject<ProfessorsResult> { req, res, result ->
            val (professorResult, err) = result
            err?.let { it1 -> co.resumeWithException(it1)
                Log.d("hoge", it1.toString())}
            professorResult?.body?.let { co.resume(it) }
        }
    }

    suspend fun professorByNickname(word: String) = suspendCoroutine<List<Body>> { co ->
        "https://~~.amazonaws.com/v1/professors".httpGet(
            listOf("key" to "nickname", "nickname" to word)
        ).responseObject<ProfessorsResult> { req, res, result ->
            val (professorResult, err) = result
            err?.let { it1 -> co.resumeWithException(it1)
                Log.d("hoge", it1.toString())}
            professorResult?.body?.let { co.resume(it) }
        }
    }
}