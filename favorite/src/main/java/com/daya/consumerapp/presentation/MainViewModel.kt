package com.daya.consumerapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.daya.consumerapp.presentation.contentprovider.ProviderContract
import com.daya.consumerapp.data.general.GeneralBio
import com.daya.consumerapp.utils.toListGeneralBio

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _getAllFav = liveData {
        val listGeneralBioCursor = app.contentResolver.query(ProviderContract.CONTENT_URI, null, null, null, null)
        val listGeneralBio = listGeneralBioCursor?.toListGeneralBio()
        if (listGeneralBio == null) {
            emit(emptyList<GeneralBio>())
        } else {
            emit(listGeneralBio)
        }
    }

    val getAllFav get() = _getAllFav
}