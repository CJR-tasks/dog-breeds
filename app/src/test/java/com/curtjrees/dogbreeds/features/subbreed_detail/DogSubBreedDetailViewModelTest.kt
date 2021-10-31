package com.curtjrees.dogbreeds.features.subbreed_detail

import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
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
class DogSubBreedDetailViewModelTest {

    private val dispatchers = CoroutineDispatchers(
        default = TestCoroutineDispatcher(),
        io = TestCoroutineDispatcher(),
        main = TestCoroutineDispatcher(),
        unconfined = TestCoroutineDispatcher(),
    )

    private val dogBreedsDataSource = mockk<DogBreedsDataSource>()

    private val viewModel = DogSubBreedDetailViewModel(
        dispatchers = dispatchers,
        dogBreedsDataSource = dogBreedsDataSource
    )

    @Test
    fun `given successful loadData call, verify viewState is updated with data`() = runBlockingTest {
        //Given
        val imagesList = mockk<List<String>>()
        val dogSubBreedItem = DogSubBreedItem(name = "subBreed", breedName = "breed", images = imagesList)
        coEvery { dogBreedsDataSource.getDogSubBreedImages(breedName = dogSubBreedItem.breedName, subBreedName = dogSubBreedItem.name) } returns imagesList

        val expected = listOf(
            DogSubBreedDetailViewModel.ViewState(),
            DogSubBreedDetailViewModel.ViewState(dogSubBreed = dogSubBreedItem),
        )

        val viewStateValues = mutableListOf<DogSubBreedDetailViewModel.ViewState>()
        val job = launch {
            viewModel.viewState.toList(viewStateValues)
        }

        //Perform
        viewModel.loadData(dogSubBreedItem)
        job.cancel()

        //Verify
        assertEquals(expected, viewStateValues)
    }
}