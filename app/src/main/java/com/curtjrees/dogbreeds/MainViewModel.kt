package com.curtjrees.dogbreeds

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator
): ViewModel() {

    val navigationEventFlow: Flow<NavigationEvent> get() = navigator.navigationEventFlow

    fun initialNavigation() {
        navigator.sendNavigationEvent(BreedListEvent)
    }

}