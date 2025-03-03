package com.pawcare.client

import com.pawcare.client.di.apiModule
import com.pawcare.client.di.componentModule
import com.pawcare.client.di.networkModule
import com.pawcare.client.di.repositoryModule
import com.pawcare.client.di.storageModule
import com.pawcare.client.di.useCaseModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.module.Module

fun initializeApp() {
    initLogger()
}

fun initLogger() {
    Napier.base(DebugAntilog())
}

// 모듈 목록만 반환하는 함수
fun getKoinModules(): List<Module> {
    return listOf(
        networkModule,
        storageModule,
        apiModule,
        repositoryModule,
        useCaseModule,
        componentModule
    )
}