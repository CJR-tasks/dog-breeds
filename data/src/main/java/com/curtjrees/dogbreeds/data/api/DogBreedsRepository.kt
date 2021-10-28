package com.curtjrees.dogbreeds.data.api

import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import kotlinx.coroutines.withContext

class DogBreedsRepository(
    private val service: DogBreedsService,
    private val dispatchers: CoroutineDispatchers,
    private val mapper: ApiDogBreedMapper
) : DogBreedsDataSource {

    override suspend fun getDogBreeds(): List<DogBreed> = withContext(dispatchers.io) {
        val response = service.getDogBreeds()
        val responseData = requireNotNull(response.body()).message
        ApiDogBreedMapper.map(responseData)
    }

}