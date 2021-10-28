package com.curtjrees.dogbreeds.entities

data class DogBreedItem(
    val name: String,
    val subBreeds: List<DogSubBreedItem>
)

data class DogSubBreedItem(
    val name: String
)