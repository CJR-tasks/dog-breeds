package com.curtjrees.dogbreeds

import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.SubBreed

data class TestDogBreed(
    override val name: String,
    override val subBreeds: List<TestSubBreed>
): DogBreed

data class TestSubBreed(
    override val name: String
): SubBreed