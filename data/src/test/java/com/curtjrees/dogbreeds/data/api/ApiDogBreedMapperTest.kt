package com.curtjrees.dogbreeds.data.api

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiDogBreedMapperTest {

    private val mapper = ApiDogBreedMapper

    @Test
    fun `given a valid map of data, verify mapBreeds returns an expected list of ApiDogBreed`() {
        //Given
        val data = mapOf(
            "one" to listOf("first", "second", "third"),
            "two" to listOf("fourth"),
        )
        val expected = listOf(
            ApiDogBreed(
                name = "one",
                subBreeds = listOf(ApiSubBreed("first"), ApiSubBreed("second"), ApiSubBreed("third"))
            ),
            ApiDogBreed(
                name = "two",
                subBreeds = listOf(ApiSubBreed("fourth"))
            )
        )

        //Perform
        val result = mapper.mapBreeds(data)

        //Verify
        assertEquals(expected, result)
    }

    @Test
    fun `given an empty map of data, verify mapBreeds returns an empty list`() {
        //Given
        val data = emptyMap<String, List<String>>()

        //Perform
        val result = mapper.mapBreeds(data)

        //Verify
        assert(result.isEmpty())
    }

    @Test
    fun `given a valid list of data, verify mapSubBreeds returns an expected list of ApiSubBreed`() {
        //Given
        val data = listOf("first", "second", "third")
        val expected = listOf(ApiSubBreed("first"), ApiSubBreed("second"), ApiSubBreed("third"))

        //Perform
        val result = mapper.mapSubBreeds(data)

        //Verify
        assertEquals(expected, result)
    }

    @Test
    fun `given an empty list of data, verify mapSubBreeds returns an empty list`() {
        //Given
        val data = emptyList<String>()

        //Perform
        val result = mapper.mapSubBreeds(data)

        //Verify
        assert(result.isEmpty())
    }


}