package com.pawcare.client.data.api

import com.pawcare.client.data.model.constend.ApiResponse
import com.pawcare.client.data.model.UserDto
import com.pawcare.client.domain.model.auth.AuthResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class AuthApi(
    private val client: HttpClient,
    private val tokenProvider : TokenProvider
) {
    suspend fun socialLogin(
        accessToken: String,
        provider: String
    ): ApiResponse<AuthResult> {
        return client.post("/api/v1/auth/login/oauth2") {
            parameter("accessToken", accessToken)
            parameter("provider", provider)
        }.body()
    }

    suspend fun getUserProfile(): ApiResponse<UserDto> {
        return client.get("/api/user/profile") {
            header("Authorization", "Bearer ${tokenProvider.getAccessToken()}")
        }.body()
    }
}