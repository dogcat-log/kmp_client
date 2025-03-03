package com.pawcare.client.domain.repository

import com.pawcare.client.domain.model.pet.Pet

interface PetRepository {
    suspend fun getPets(): Result<List<Pet>>
    suspend fun addPet(pet: Pet): Result<Pet>
    suspend fun updatePet(pet: Pet): Result<Pet>
    suspend fun deletePet(petId: String): Result<Unit>
}