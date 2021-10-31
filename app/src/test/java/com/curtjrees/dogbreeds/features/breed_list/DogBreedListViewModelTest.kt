package com.curtjrees.dogbreeds.features.breed_list

import com.curtjrees.dogbreeds.BreedDetailEvent
import com.curtjrees.dogbreeds.Navigator
import com.curtjrees.dogbreeds.SubBreedDetailEvent
import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
import com.curtjrees.dogbreeds.mappers.DogBreedItemMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
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
class DogBreedListViewModelTest {

    private val dispatchers = CoroutineDispatchers(
        default = TestCoroutineDispatcher(),
        io = TestCoroutineDispatcher(),
        main = TestCoroutineDispatcher(),
        unconfined = TestCoroutineDispatcher(),
    )

    private val navigator = mockk<Navigator>()
    private val dogBreedsDataSource = mockk<DogBreedsDataSource>()
    private val mapper = mockk<DogBreedItemMapper>()

    private val viewModel = DogBreedListViewModel(
        dispatchers = dispatchers,
        navigator = navigator,
        dogBreedsDataSource = dogBreedsDataSource,
        mapper = mapper
    )

    @Test
    fun `given successful loadData call, verify viewState is loading, then updated with data`() = runBlockingTest {
        //Given
        val dogBreedsData = mockk<List<DogBreedItem>>()
        coEvery { dogBreedsDataSource.getDogBreeds() } returns mockk()
        every { mapper.mapBreeds(any()) } returns dogBreedsData

        val expected = listOf(
            DogBreedListViewModel.ViewState(),
            DogBreedListViewModel.ViewState(loading = true),
            DogBreedListViewModel.ViewState(dogBreeds = dogBreedsData, loading = false),
        )

        val viewStateValues = mutableListOf<DogBreedListViewModel.ViewState>()
        val job = launch {
            viewModel.viewState.toList(viewStateValues)
        }

        //Perform
        viewModel.loadData()
        job.cancel()

        //Verify
        assertEquals(expected, viewStateValues)
    }

    @Test
    fun `given error loadData call, verify viewState is loading, then updated with error`() = runBlockingTest {
        //Given
        val testError = Exception("Test Error")
        coEvery { dogBreedsDataSource.getDogBreeds() } throws testError

        val expected = listOf(
            DogBreedListViewModel.ViewState(),
            DogBreedListViewModel.ViewState(loading = true),
            DogBreedListViewModel.ViewState(error = testError, loading = false),
        )

        val viewStateValues = mutableListOf<DogBreedListViewModel.ViewState>()
        val job = launch {
            viewModel.viewState.toList(viewStateValues)
        }

        //Perform
        viewModel.loadData()
        job.cancel()

        //Verify
        assertEquals(expected, viewStateValues)
    }

    @Test
    fun `given onBreedClicked called, verify BreedDetailEvent is sent to navigator`() {
        //Given
        val dogBreedItem = DogBreedItem(name = "breed", subBreeds = emptyList())
        every { navigator.sendNavigationEvent(any()) } just runs

        //Perform
        viewModel.onBreedClicked(dogBreedItem)

        //Verify
        verify(exactly = 1) { navigator.sendNavigationEvent(BreedDetailEvent(dogBreedItem)) }
    }

    @Test
    fun `given onSubBreedClicked called, verify BreedDetailEvent is sent to navigator`() {
        //Given
        val dogSubBreedItem = DogSubBreedItem(name = "subBreed", breedName = "breed")
        every { navigator.sendNavigationEvent(any()) } just runs

        //Perform
        viewModel.onSubBreedClicked(dogSubBreedItem)

        //Verify
        verify(exactly = 1) { navigator.sendNavigationEvent(SubBreedDetailEvent(dogSubBreedItem)) }
    }

}