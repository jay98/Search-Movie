package com.example.searchmovie.util

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.OpenForTesting
import com.example.searchmovie.domain.models.Config
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton


// Basically a wrapper to store and fetch config in sharedpref. Ideally would store in db using
// Room or if we want something like Paper
@Singleton
class ConfigManager @Inject constructor(private val gson: Gson) {

    private lateinit var prefs: SharedPreferences
    private var isInit: Boolean = false
    private var config: Config? = null

    fun init(initContext: Context) {
        prefs = initContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        isInit = true
    }

    fun setConfig(newConfig: Config?) {
        if (!isInit) throwUnInitException()
        config = newConfig
        prefs.edit()
            .putString(CONFIG_KEY, gson.toJson(newConfig))
            .apply()

    }

    fun getConfig(): Config? {
        if (!isInit) throwUnInitException()

        config?.let {
            return it
        }


        prefs.getString(CONFIG_KEY, null).let {
            config = gson.fromJson(it, Config::class.java)
            return config
        }
    }

    private fun throwUnInitException() {
        throw UninitializedPropertyAccessException("ConfigManager not initialized pleases call init()")
    }


    companion object {
        private const val PREF_NAME = "ConfigPrefs"
        private const val CONFIG_KEY = "config"
    }
}
