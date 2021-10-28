package com.curtjrees.dogbreeds

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.data.domain.DogBreed
import com.curtjrees.dogbreeds.data.domain.DogBreedsDataSource
import com.curtjrees.dogbreeds.data.domain.SubBreed
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
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
    private val dogBreedsDataSource: DogBreedsDataSource
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val data = dogBreedsDataSource.getDogBreeds()
            val items = DogBreedItemMapper.mapList(data)
            withContext(dispatchers.main) {
                _viewState.update { state ->
                    state.copy(dogBreeds = items)
                }
            }
        }
    }

    data class ViewState(
        val dogBreeds: List<DogBreedItem> = emptyList()
    )

}

object DogBreedItemMapper {

    fun mapList(data: List<DogBreed>): List<DogBreedItem> = data.map(::map)

    fun map(data: DogBreed): DogBreedItem = DogBreedItem(
        name = data.name,
        subBreeds = mapSubBreeds(data.subBreeds),
    )

    @VisibleForTesting
    internal fun mapSubBreeds(data: List<SubBreed>): List<DogSubBreedItem> = data.map(::map)

    @VisibleForTesting
    internal fun map(data: SubBreed): DogSubBreedItem = DogSubBreedItem(
        name = data.name
    )

}

