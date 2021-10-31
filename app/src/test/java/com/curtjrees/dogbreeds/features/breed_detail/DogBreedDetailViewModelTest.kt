package com.curtjrees.dogbreeds.features.breed_detail

import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.entities.DogBreedItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DogBreedDetailViewModelTest {

    private val dispatchers = CoroutineDispatchers(
        default = TestCoroutineDispatcher(),
        io = TestCoroutineDispatcher(),
        main = TestCoroutineDispatcher(),
        unconfined = TestCoroutineDispatcher(),
    )

    private val dogBreedsDataSource = mockk<DogBreedsDataSource>()

    private val viewModel = DogBreedDetailViewModel(
        dispatchers = dispatchers,
        dogBreedsDataSource = dogBreedsDataSource
    )

    @Test
    fun `given successful loadData call, verify viewState is updated with data`() = runBlockingTest {
        //Given
        val imagesList = mockk<List<String>>()
        val dogBreedItem = DogBreedItem(name = "breed", subBreeds = emptyList(), images = imagesList)
        coEvery { dogBreedsDataSource.getDogBreedImages(dogBreedItem.name) } returns imagesList

        val expected = listOf(
            DogBreedDetailViewModel.ViewState(),
            DogBreedDetailViewModel.ViewState(dogBreed = dogBreedItem),
        )

        val viewStateValues = mutableListOf<DogBreedDetailViewModel.ViewState>()
        val job = launch {
            viewModel.viewState.toList(viewStateValues)
        }

        //Perform
        viewModel.loadData(dogBreedItem)
        job.cancel()

        //Verify
        assertEquals(expected, viewStateValues)
    }

}