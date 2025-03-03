package com.pawcare.client.data.repository

import com.pawcare.client.data.api.AuthApi
import com.pawcare.client.data.api.TokenProvider
import com.pawcare.client.domain.model.auth.AuthResult
import com.pawcare.client.domain.model.auth.User
import com.pawcare.client.data.model.constend.ApiResponse
import com.pawcare.client.data.model.mapper.toDomain
import com.pawcare.client.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {

    override suspend fun socialLogin(provider: String, accessToken: String): Result<AuthResult> {
        return try {
            val response = authApi.socialLogin(provider, accessToken)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserProfile(token: String): Result<ApiResponse<User>> {
        return try {
            val response = authApi.getUserProfile()

            val userResponse = ApiResponse(
                success = response.success,
                message = response.message,
                data = response.data.toDomain()
            )

            Result.success(userResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}