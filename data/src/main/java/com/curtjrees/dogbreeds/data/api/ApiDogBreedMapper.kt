package com.curtjrees.dogbreeds.data.api

object ApiDogBreedMapper {

    fun map(data: Map<String, List<String>>): List<ApiDogBreed> =
        data.map { (breed, subBreeds) ->
            ApiDogBreed(
                name = breed,
                subBreeds = mapSubBreeds(subBreeds)
            )
        }

    private fun mapSubBreeds(data: List<String>): List<ApiSubBreed> =
        data.map { name ->
            ApiSubBreed(name)
        }
}