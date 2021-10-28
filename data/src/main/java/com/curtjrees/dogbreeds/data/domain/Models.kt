package com.curtjrees.dogbreeds.data.domain

interface DogBreed {
    val name: String
    val subBreeds: List<SubBreed>
}

interface SubBreed {
    val name: String
}

