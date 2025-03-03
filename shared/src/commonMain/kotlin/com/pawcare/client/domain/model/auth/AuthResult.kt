package com.pawcare.client.domain.model.auth

data class AuthResult(
    val accessToken: String,
    val refreshToken: String
)