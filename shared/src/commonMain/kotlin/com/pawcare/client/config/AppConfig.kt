package com.pawcare.client.config

expect object AppConfig {
    /**
     * 네이버 클라이언트 ID
     */
    val NAVER_CLIENT_ID: String

    /**
     * 네이버 클라이언트 시크릿
     */
    val NAVER_CLIENT_SECRET: String

    /**
     * 구글 클라이언트 ID
     */
    val GOOGLE_CLIENT_ID: String

    /**
     * 앱 이름
     */
    val APP_NAME: String

    /**
     * 카카오 앱 키
     */
    val KAKAO_APP_KEY: String

    /**
     * API 기본 URL
     */
    val BASE_API_URL: String
}