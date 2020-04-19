package io.github.hunachi.appointment.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.hunachi.appointment.data.Professor
import io.github.hunachi.appointment.data.SearchKey
import io.github.hunachi.appointment.infra.professors.ProfessorsUseCase
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val professorUserCase =
        ProfessorsUseCase()

    private val _professors = MutableLiveData<List<Professor>>()
    val professors: LiveData<List<Professor>> = _professors

    private val _keys = MutableLiveData<List<SearchKey>>()
    val keys: LiveData<List<SearchKey>> = _keys

    fun refreshProfessors(word: String = "") {
        try {
            viewModelScope.launch {
                val key = if (word.isBlank()) "" else
                    keys.value?.find { it.isActive }?.key ?: ""
                val result = professorUserCase.professors(key, word)
                //val result = professorUserCase.professors("name", "本田真久")
                _professors.value = result
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    fun setUpKeys(list: List<SearchKey>) {
        _keys.value = list
    }

    fun refreshKeys(key: SearchKey) {
        Log.d("hoge1", keys.value?.find { it.key == key.key }.toString())
        var active = keys.value?.toMutableList()?.find {
            key == it
        }?.isActive ?: false
        val list = if (active) {
            keys.value?.map { searchKey -> searchKey.apply { isActive = false } }
        } else {
            keys.value?.map {
                if (key.key == it.key) it.apply { isActive = true }
                else it.apply { isActive = false }
            }
        }
        Log.d("hoge2", list?.find { it.key == key.key }.toString())
        _keys.value = list
    }
}
