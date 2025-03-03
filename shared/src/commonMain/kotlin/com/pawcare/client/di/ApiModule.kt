package com.pawcare.client.di

import com.pawcare.client.data.api.AuthApi
import org.koin.dsl.module

val apiModule = module {
    single { AuthApi(get(), get()) }
}