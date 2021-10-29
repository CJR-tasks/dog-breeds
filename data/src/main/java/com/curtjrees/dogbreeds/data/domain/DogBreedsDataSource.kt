package com.curtjrees.dogbreeds.data.domain

interface DogBreedsDataSource {

    suspend fun getDogBreeds(): List<DogBreed>
    suspend fun getDogBreedImages(breedName: String): List<String>
    suspend fun getDogSubBreedImages(breedName: String, subBreedName: String): List<String>

}