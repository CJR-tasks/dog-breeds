package com.curtjrees.dogbreeds.mappers

import androidx.annotation.VisibleForTesting
import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.SubBreed
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem

object DogBreedItemMapper {

    fun mapList(data: List<DogBreed>): List<DogBreedItem> = data.map(DogBreedItemMapper::map)

    fun map(data: DogBreed): DogBreedItem = DogBreedItem(
        name = data.name,
        subBreeds = mapSubBreeds(data.subBreeds, data.name),
    )

    @VisibleForTesting
    internal fun mapSubBreeds(data: List<SubBreed>, breedName: String): List<DogSubBreedItem> = data.map { map(it, breedName) }

    @VisibleForTesting
    internal fun map(data: SubBreed, breedName: String): DogSubBreedItem = DogSubBreedItem(
        name = data.name,
        breedName = breedName
    )

}