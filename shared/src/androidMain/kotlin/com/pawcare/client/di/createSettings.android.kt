package com.pawcare.client.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidSettingsProvider : KoinComponent {
    private val context: Context by inject()

    fun createSettings(): Settings {
        return SharedPreferencesSettings(
            context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        )
    }
}

actual fun createSettings(): Settings = AndroidSettingsProvider().createSettings()
