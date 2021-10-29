package com.curtjrees.dogbreeds.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsService {

    @GET("/api/breeds/list/all")
    suspend fun getDogBreeds(): Response<ApiDogBreedsResponse>

    @GET("/api/breed/{breedName}/images")
    suspend fun getDogBreedImages(
        @Path("breedName") breedName: String
    ): Response<ApiDogBreedImagesResponse>

    @GET("/api/breed/{breedName}/{subBreedName}/images")
    suspend fun getDogSubBreedImages(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String
    ): Response<ApiDogBreedImagesResponse>

}