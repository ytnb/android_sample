package com.example.ytnb.databinding2

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

class MainForm {
    val to = ObservableField<String>("")
    val subject = ObservableField<String>("")
    val message = ObservableField<String>("")
    val valid = ObservableBoolean(true)
    val requesting = ObservableBoolean()

    val onComplete = MutableLiveData<Boolean>()

    fun validate() {
        val result = !to.get().isNullOrBlank()
        valid.set(result)

        if (result) {
            requesting.set(true)
            send()
        }
    }

    private fun send() {
        android.os.Handler().postDelayed({ onComplete.postValue(true) }, 3000)
    }
}

