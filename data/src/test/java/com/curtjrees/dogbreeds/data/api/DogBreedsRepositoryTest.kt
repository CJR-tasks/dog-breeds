package com.curtjrees.dogbreeds.data.api

import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DogBreedsRepositoryTest {

    private val dispatchers = CoroutineDispatchers(
        default = TestCoroutineDispatcher(),
        io = TestCoroutineDispatcher(),
        main = TestCoroutineDispatcher(),
        unconfined = TestCoroutineDispatcher(),
    )

    private val service = mockk<DogBreedsService>()
    private val mapper = mockk<ApiDogBreedMapper>()

    private val repository = DogBreedsRepository(
        service = service,
        dispatchers = dispatchers,
        mapper = mapper
    )

    @Test
    fun `given a successful response from service, verify getDogBreeds returns a list of DogBreed`() = runBlockingTest {
        //Given
        val data = mapOf("breed" to listOf("subbreed"))
        coEvery { service.getDogBreeds() } returns Response.success(ApiDogBreedsResponse(status = "success", message = data))

        val expected = listOf(ApiDogBreed(name = "breed", subBreeds = listOf(ApiSubBreed(name = "subbreed"))))
        every { mapper.mapBreeds(data) } returns expected

        //Perform
        val result = repository.getDogBreeds()

        //Verify
        assertEquals(expected, result)
    }

    @Test(expected = NoSuchElementException::class)
    fun `given an error response from service, verify getDogBreeds throws a NoSuchElementException`() = runBlockingTest {
        //Given
        coEvery { service.getDogBreeds() } returns Response.error(400, ResponseBody.create(MediaType.parse("text"), "error"))

        //Perform
        repository.getDogBreeds()
    }

    @Test
    fun `given a successful response from service, verify getDogBreedImages returns a list of image urls`() = runBlockingTest {
        //Given
        val breedName = "breed"
        val responseData = mockk<List<String>>()
        coEvery { service.getDogBreedImages(breedName) } returns Response.success(ApiDogBreedImagesResponse(status = "success", message = responseData))

        //Perform
        val result = repository.getDogBreedImages(breedName)

        //Verify
        assertEquals(responseData, result)
    }

    @Test(expected = NoSuchElementException::class)
    fun `given an error response from service, verify getDogBreedImages throws a NoSuchElementException`() = runBlockingTest {
        //Given
        val breedName = "breed"
        coEvery { service.getDogBreedImages(any()) } returns Response.error(400, ResponseBody.create(MediaType.parse("text"), "error"))

        //Perform
        repository.getDogBreedImages(breedName)
    }

    @Test
    fun `given a successful response from service, verify getDogSubBreedImages returns a list of image urls`() = runBlockingTest {
        //Given
        val breedName = "breed"
        val subBreedName = "subBreed"
        val responseData = mockk<List<String>>()
        coEvery { service.getDogSubBreedImages(any(), any()) } returns Response.success(ApiDogBreedImagesResponse(status = "success", message = responseData))

        //Perform
        val result = repository.getDogSubBreedImages(breedName = breedName, subBreedName = subBreedName)

        //Verify
        assertEquals(responseData, result)
    }

    @Test(expected = NoSuchElementException::class)
    fun `given an error response from service, verify getDogSubBreedImages throws a NoSuchElementException`() = runBlockingTest {
        //Given
        val breedName = "breed"
        val subBreedName = "subBreed"
        coEvery { service.getDogSubBreedImages(any(), any()) } returns Response.error(400, ResponseBody.create(MediaType.parse("text"), "error"))

        //Perform
        repository.getDogSubBreedImages(breedName = breedName, subBreedName = subBreedName)
    }
}