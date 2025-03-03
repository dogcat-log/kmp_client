package com.pawcare.client.domain.repository

import com.pawcare.client.domain.model.auth.AuthResult
import com.pawcare.client.domain.model.auth.User
import com.pawcare.client.data.model.constend.ApiResponse

interface AuthRepository {
    suspend fun socialLogin(provider: String, accessToken: String): Result<AuthResult>
    suspend fun getUserProfile(token: String): Result<ApiResponse<User>>
}
