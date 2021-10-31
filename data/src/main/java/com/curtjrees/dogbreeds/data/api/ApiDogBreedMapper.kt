package com.curtjrees.dogbreeds.data.api

import androidx.annotation.VisibleForTesting

object ApiDogBreedMapper {

    fun mapBreeds(data: Map<String, List<String>>): List<ApiDogBreed> =
        data.map { (breed, subBreeds) ->
            ApiDogBreed(
                name = breed,
                subBreeds = mapSubBreeds(subBreeds)
            )
        }

    @VisibleForTesting
    internal fun mapSubBreeds(data: List<String>): List<ApiSubBreed> =
        data.map { name ->
            ApiSubBreed(name)
        }
}