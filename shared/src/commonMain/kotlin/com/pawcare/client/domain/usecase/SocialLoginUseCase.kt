package com.pawcare.client.domain.usecase

import com.pawcare.client.domain.model.auth.AuthResult
import com.pawcare.client.domain.repository.AuthRepository

class SocialLoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(accessToken: String, provider: String): Result<AuthResult> {
        return repository.socialLogin(accessToken, provider)
    }
}
