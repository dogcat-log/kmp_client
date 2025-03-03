package com.pawcare.client.data.api

import com.russhwolf.settings.Settings

class TokenProvider(
    private val settings: Settings
) {
    fun getAccessToken(): String = settings.getString(ACCESS_TOKEN_KEY, "")
    fun getRefreshToken(): String = settings.getString(REFRESH_TOKEN_KEY, "")

    fun saveToken(accessToken: String, refreshToken: String) {
        settings.putString(ACCESS_TOKEN_KEY, accessToken)
        settings.putString(REFRESH_TOKEN_KEY, refreshToken)
    }
    fun clearTokens() {
        settings.putString(ACCESS_TOKEN_KEY, "")
        settings.putString(REFRESH_TOKEN_KEY, "")
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}

