package com.pawcare.client.presentation.state

import com.pawcare.client.domain.model.auth.User


sealed class AuthState {
    data object Initial : AuthState()
    data object Loading : AuthState()
    data object Unauthenticated : AuthState()
    data class Authenticated(val user: User? = null) : AuthState()
    data class Error(val message: String) : AuthState()

}