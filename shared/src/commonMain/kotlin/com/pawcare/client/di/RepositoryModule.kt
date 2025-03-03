package com.pawcare.client.di

import com.pawcare.client.data.repository.AuthRepositoryImpl
import com.pawcare.client.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}