package com.mufr.klinikku.shared

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {
    const val BASE_URL = "http://13.211.168.25:8080/"
    const val AUTH_PREFERENCES = "AUTH_PREF"
    val AUTH_KEY = stringPreferencesKey("auth_key")

    const val GENDER_MALE = "Laki-laki"
    const val GENDER_FEMALE = "Perempuan"
}