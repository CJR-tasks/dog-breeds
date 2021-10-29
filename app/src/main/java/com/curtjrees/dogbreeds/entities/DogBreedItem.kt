package com.curtjrees.dogbreeds.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogBreedItem(
    val name: String,
    val subBreeds: List<DogSubBreedItem>,
    val images: List<String> = emptyList()
) : Parcelable

@Parcelize
data class DogSubBreedItem(
    val name: String,
    val breedName: String,
    val images: List<String> = emptyList()
) : Parcelable