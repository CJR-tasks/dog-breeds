package com.curtjrees.dogbreeds

import com.curtjrees.dogbreeds.data.CoroutineDispatchers
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(coroutineDispatchers: CoroutineDispatchers) {
    private val coroutineScope = CoroutineScope(coroutineDispatchers.main)

    private val _navigationEventFlow = MutableSharedFlow<NavigationEvent>()
    val navigationEventFlow: SharedFlow<NavigationEvent> = _navigationEventFlow

    fun sendNavigationEvent(event: NavigationEvent) {
        coroutineScope.launch {
            _navigationEventFlow.emit(event)
        }
    }
}

sealed class NavigationEvent
object BreedListEvent : NavigationEvent()
data class BreedDetailEvent(val breed: DogBreedItem) : NavigationEvent()
data class SubBreedDetailEvent(val subBreed: DogSubBreedItem) : NavigationEvent()