package com.pawcare.client.platform.social

expect class SocialLogin {
    suspend fun loginWithKakao(context: Any): Result<String>
    suspend fun loginWithGoogle(context: Any): Result<String>
    suspend fun loginWithNaver(context: Any): Result<String>

}