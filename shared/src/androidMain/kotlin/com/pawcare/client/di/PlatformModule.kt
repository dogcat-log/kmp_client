package com.pawcare.client.di

import com.pawcare.client.platform.social.SocialLogin
import org.koin.dsl.module

val socialLoginModule = module {
    single { SocialLogin() }
}