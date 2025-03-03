package com.pawcare.client.config

import com.pawcare.client.BuildConfig

actual object AppConfig {
    /**
     * 네이버 클라이언트 ID
     */
    actual val NAVER_CLIENT_ID: String = BuildConfig.NAVER_CLIENT_ID


    /**
     * 네이버 클라이언트 시크릿
     */
    actual val NAVER_CLIENT_SECRET: String = BuildConfig.NAVER_CLIENT_SECRET

    /**
     * 구글 클라이언트 ID
     */
    actual val GOOGLE_CLIENT_ID: String = BuildConfig.GOOGLE_CLIENT_ID

    /**
     * 앱 이름
     */
    actual val APP_NAME: String = BuildConfig.APP_NAME

    /**
     * 카카오 앱 키
     */
    actual val KAKAO_APP_KEY: String = BuildConfig.KAKAO_APP_KEY

    /**
     * API 기본 URL
     */
    actual val BASE_API_URL: String = BuildConfig.BASE_API_URL

}