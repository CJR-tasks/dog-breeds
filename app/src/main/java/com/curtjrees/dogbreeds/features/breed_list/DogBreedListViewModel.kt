package com.curtjrees.dogbreeds.features.breed_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtjrees.dogbreeds.BreedDetailEvent
import com.curtjrees.dogbreeds.Navigator
import com.curtjrees.dogbreeds.SubBreedDetailEvent
import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
import com.curtjrees.dogbreeds.mappers.DogBreedItemMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogBreedListViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: Navigator,
    private val dogBreedsDataSource: DogBreedsDataSource,
    private val mapper: DogBreedItemMapper
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    fun loadData() {
        viewModelScope.launch(dispatchers.default) {
            withContext(dispatchers.main) {
                _viewState.update { state -> state.copy(loading = true) }
            }

            runCatching {
                val data = dogBreedsDataSource.getDogBreeds()
                val items = mapper.mapBreeds(data)
                withContext(dispatchers.main) {
                    _viewState.update { state -> state.copy(dogBreeds = items, error = null, loading = false) }
                }
            }.onFailure { error ->
                withContext(dispatchers.main) {
                    _viewState.update { state -> state.copy(error = error, loading = false) }
                }
            }
        }
    }

    fun onBreedClicked(dogBreedItem: DogBreedItem) {
        navigator.sendNavigationEvent(BreedDetailEvent(dogBreedItem))
    }

    fun onSubBreedClicked(subBreedItem: DogSubBreedItem) {
        navigator.sendNavigationEvent(SubBreedDetailEvent(subBreedItem))
    }

    data class ViewState(
        val loading: Boolean = false,
        val error: Throwable? = null,
        val dogBreeds: List<DogBreedItem> = emptyList()
    )

}

