package com.pawcare.client.domain.model.permission

data class Permission(
    val type: PermissionType,
    val title: String,
    val description: String
)

enum class PermissionType {
    NOTIFICATION,
    CAMERA,
    LOCATION
}