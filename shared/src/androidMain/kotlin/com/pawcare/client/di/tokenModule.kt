package com.pawcare.client.di

import com.pawcare.client.data.api.TokenProvider
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val tokenModule = module {
    single { TokenProvider(get<Settings>()) }
}