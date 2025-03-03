package com.pawcare.client.data.model.mapper

import com.pawcare.client.data.model.UserDto
import com.pawcare.client.domain.model.auth.User

fun UserDto.toDomain(): User {
    return User(
        uid = this.uid,
        email = this.email,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
        authProvider = this.authProvider,
        profileImageUrl = this.profileImageUrl,
        userRoles = this.userRoles,
        familyActive = this.familyActive,
        familyName = this.familyName,
    )
}
