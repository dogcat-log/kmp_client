package com.pawcare.client.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module

expect fun createSettings(): Settings

val storageModule = module {
    single { createSettings() }
}