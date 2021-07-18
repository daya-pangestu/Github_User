package com.daya.core.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> LiveData<T>.debounce(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,text,duration).show()
}