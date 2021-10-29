package com.curtjrees.dogbreeds.features.breed_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.entities.DogBreedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogBreedDetailViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val dogBreedsDataSource: DogBreedsDataSource
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    fun loadData(item: DogBreedItem) {
        viewModelScope.launch(dispatchers.default) {
            val images = dogBreedsDataSource.getDogBreedImages(breedName = item.name)
            val newItem = item.copy(images = images)

            withContext(dispatchers.main) {
                _viewState.update { state ->
                    state.copy(dogBreed = newItem)
                }
            }
        }
    }

    data class ViewState(
        val dogBreed: DogBreedItem? = null
    )
}