package com.pawcare.client.data.model.constend

data class ApiResponse<T> (
    val success: String,
    val message: String,
    val data: T
)