package com.pawcare.client.domain.model.auth

data class User(
    val uid: String,
    val email: String,
    val userName: String,
    val phoneNumber: String?,
    val authProvider: String,
    val profileImageUrl: String?,
    val userRoles: String,
    val familyActive: Boolean,
    val familyName: String?
)