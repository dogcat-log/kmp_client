package com.pawcare

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.pawcare.client.BuildConfig
import com.pawcare.client.config.AppConfig
import com.pawcare.client.di.socialLoginModule
import com.pawcare.client.di.tokenModule
import com.pawcare.client.getKoinModules
import com.pawcare.client.initLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class PawCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 로거 초기화
        initLogger()

        // Koin 초기화 (Android Context 포함)
        startKoin {
            androidContext(this@PawCareApplication)
            modules(getKoinModules())
        }

        // 카카오 SDK 초기화
        KakaoSdk.init(this, AppConfig.KAKAO_APP_KEY)
        // 네이버 SDK 초기화
        NaverIdLoginSDK.initialize(
            this,
            AppConfig.NAVER_CLIENT_ID,
            AppConfig.NAVER_CLIENT_SECRET,
            AppConfig.APP_NAME
        )

        // 소셜 로그인 모듈 로드
        loadKoinModules(socialLoginModule)

        // TokenProvider 모듈 로드
        loadKoinModules(tokenModule)
    }
}