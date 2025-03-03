package com.pawcare.client.di

import com.pawcare.client.domain.usecase.GetUserProfileUseCase
import com.pawcare.client.domain.usecase.SocialLoginUseCase
import org.koin.dsl.module

val useCaseModule= module {
    factory { SocialLoginUseCase(get()) }
    factory { GetUserProfileUseCase(get(), get()) }
}