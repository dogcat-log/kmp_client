package com.pawcare.client.domain.model.auth

enum class SocialProvider {
    KAKAO,
    GOOGLE,
    NAVER;

    fun toServerString(): String {
        return when (this) {
            KAKAO -> "kakao"
            GOOGLE -> "google"
            NAVER -> "naver"
        }
    }
}