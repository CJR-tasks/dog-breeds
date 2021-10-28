package com.curtjrees.dogbreeds.data.api

import retrofit2.Response
import retrofit2.http.GET

interface DogBreedsService {

    @GET("/api/breeds/list/all")
    suspend fun getDogBreeds(): Response<ApiDogBreedsResponse>

}