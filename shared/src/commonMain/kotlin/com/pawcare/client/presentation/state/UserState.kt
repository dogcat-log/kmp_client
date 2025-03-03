package com.pawcare.client.presentation.state

import com.pawcare.client.domain.model.auth.User

sealed class UserState {
    data object Initial : UserState()
    data object Loading : UserState()
    data object LoggedIn : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message:String) : UserState()
}