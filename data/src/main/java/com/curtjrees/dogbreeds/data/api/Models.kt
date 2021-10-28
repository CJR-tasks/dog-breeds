package com.curtjrees.dogbreeds.data.api

import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.SubBreed

data class ApiDogBreedsResponse(
    val status: String,
    val message: Map<String, List<String>>
)

data class ApiDogBreed(
    override val name: String,
    override val subBreeds: List<ApiSubBreed>
) : DogBreed

data class ApiSubBreed(
    override val name: String,
) : SubBreed