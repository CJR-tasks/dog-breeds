package com.curtjrees.dogbreeds.data.domain

interface DogBreedsDataSource {

    suspend fun getDogBreeds(): List<DogBreed>

}