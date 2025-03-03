package com.pawcare.client.domain.model.pet

data class Pet(
    val id: String,
    val name: String,
    val type: String, // DOG or CAT
    val breed: String,
    val birthDate: String,
    val imageUrl: String?
)