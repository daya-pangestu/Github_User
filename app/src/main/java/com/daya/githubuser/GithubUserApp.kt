package com.daya.githubuser

import android.app.Application
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.daya.githubuser.data.Resource
import com.daya.githubuser.domain.pref.GetIsFirstRunUseCase
import com.daya.githubuser.domain.pref.SetFirstRunUseCase
import com.daya.githubuser.ui.settings.AlarmReceiver
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class GithubUserApp : Application() {

    private val scope = MainScope()

    @Inject
    lateinit var setFirstRunUsecase: SetFirstRunUseCase

    @Inject
    lateinit var isFirstRunUsecase: GetIsFirstRunUseCase


    override fun onCreate() {
        super.onCreate()
        val settings = PreferenceManager.getDefaultSharedPreferences(this)

        scope.launch {
            when (val isFirstRunRes = isFirstRunUsecase(Unit)) {
                is Resource.Success -> {
                    settings.edit {
                        if (isFirstRunRes.data) {
                            AlarmReceiver.setRepeatingAlarm(this@GithubUserApp,"09:00")
                            putBoolean(getString(R.string.key_pref_reminder), false)
                        }
                    }
                    setFirstRunUsecase(false)
                }
            }
        }
    }
}