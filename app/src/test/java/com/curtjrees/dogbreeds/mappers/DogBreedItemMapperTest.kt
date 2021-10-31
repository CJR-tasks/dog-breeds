package com.curtjrees.dogbreeds.mappers

import com.curtjrees.dogbreeds.TestDogBreed
import com.curtjrees.dogbreeds.TestSubBreed
import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.SubBreed
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DogBreedItemMapperTest {

    private val mapper = DogBreedItemMapper

    @Test
    fun `given a valid list of data, verify mapBreeds returns an expected list of DogBreedItem`() {
        //Given
        val data = listOf(TestDogBreed("breed", subBreeds = listOf(TestSubBreed("subBreed"))))
        val expected = listOf(
            DogBreedItem(name = "breed", subBreeds = listOf(DogSubBreedItem(name = "subBreed", breedName = "breed")))
        )

        //Perform
        val result = mapper.mapBreeds(data = data)

        //Verify
        assertEquals(expected, result)
    }

    @Test
    fun `given an empty list of data, verify mapBreeds returns an empty list`() {
        //Given
        val data = emptyList<DogBreed>()

        //Perform
        val result = mapper.mapBreeds(data = data)

        //Verify
        assert(result.isEmpty())
    }

    @Test
    fun `given valid DogBreed, verify mapBreed returns an expected DogBreedItem`() {
        //Given
        val data = TestDogBreed("breed", subBreeds = listOf(TestSubBreed("subBreed")))
        val expected = DogBreedItem(name = "breed", subBreeds = listOf(DogSubBreedItem(name = "subBreed", breedName = "breed")))

        //Perform
        val result = mapper.mapBreed(data = data)

        //Verify
        assertEquals(expected, result)
    }

    @Test
    fun `given a valid list of data, verify mapSubBreeds returns an expected list of DogSubBreedItem`() {
        //Given
        val breedName = "breed"
        val data = listOf(
            TestSubBreed(name = "subBreed1"),
            TestSubBreed(name = "subBreed2"),
        )
        val expected = listOf(
            DogSubBreedItem(name = "subBreed1", breedName = breedName),
            DogSubBreedItem(name = "subBreed2", breedName = breedName),
        )

        //Perform
        val result = mapper.mapSubBreeds(data = data, breedName = breedName)

        //Verify
        assertEquals(expected, result)
    }

    @Test
    fun `given an empty list of data, verify mapSubBreeds returns an empty list`() {
        //Given
        val breedName = "breed"
        val data = emptyList<SubBreed>()

        //Perform
        val result = mapper.mapSubBreeds(data = data, breedName = breedName)

        //Verify
        assert(result.isEmpty())
    }

    @Test
    fun `given valid SubBreed, verify mapSubBreed returns an expected DogSubBreedItem`() {
        //Given
        val breedName = "breed"
        val data = TestSubBreed(name = "subBreed")
        val expected = DogSubBreedItem(name = "subBreed", breedName = breedName)

        //Perform
        val result = mapper.mapSubBreed(data = data, breedName = breedName)

        //Verify
        assertEquals(expected, result)
    }

}