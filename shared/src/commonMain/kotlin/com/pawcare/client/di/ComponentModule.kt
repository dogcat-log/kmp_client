package com.pawcare.client.di

import com.arkivanov.decompose.ComponentContext
import com.pawcare.client.presentation.viewmodel.AuthComponent
import org.koin.dsl.module

val componentModule = module {
    factory { (componentContext: ComponentContext) ->
        AuthComponent(
            componentContext = componentContext,
            socialLogin = get(),
            socialLoginUseCase = get(),
            getUserProfileUseCase = get(),
            tokenProvider = get()
        )
    }
}