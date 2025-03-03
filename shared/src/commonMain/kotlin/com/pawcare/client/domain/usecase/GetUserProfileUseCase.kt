package com.pawcare.client.domain.usecase

import com.pawcare.client.data.api.TokenProvider
import com.pawcare.client.domain.model.auth.User
import com.pawcare.client.data.model.constend.ApiResponse
import com.pawcare.client.domain.repository.AuthRepository

class GetUserProfileUseCase(
    private val repository: AuthRepository,
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(): Result<ApiResponse<User>> {
        val accessToken = tokenProvider.getAccessToken()
        if(accessToken.isEmpty()) {
            return Result.failure(IllegalStateException("토큰이 유효하지 않습니다."))
        }
        return repository.getUserProfile(accessToken)
    }
}
