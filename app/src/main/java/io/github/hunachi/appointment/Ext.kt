package io.github.hunachi.appointment

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launchDataLoad(spinner: MutableLiveData<Boolean>, block: suspend () -> Unit): Job {
    return viewModelScope.launch {
        try {
            spinner.value = true
            block()
        } catch (error: Exception) {
            throw error
        } finally {
            spinner.value = false
        }
    }
}

fun <T> LiveData<T>.nonNullObserver(context: LifecycleOwner, observer: (T) -> Unit) {
    observe(context, Observer<T> {
        if (it != null) observer(it)
    })
}

fun Activity.toast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}