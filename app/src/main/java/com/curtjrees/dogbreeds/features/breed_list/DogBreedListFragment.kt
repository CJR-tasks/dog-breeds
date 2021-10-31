package com.curtjrees.dogbreeds.features.breed_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.curtjrees.dogbreeds.R
import com.curtjrees.dogbreeds.layouts.DogBreedLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogBreedListFragment : Fragment() {

    private val viewModel: DogBreedListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        ComposeView(requireContext()).apply {
            setContent {
                DogBreedScreen(viewModel)
            }
        }

}

@Composable
private fun DogBreedScreen(viewModel: DogBreedListViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewState = remember(viewModel.viewState, lifecycleOwner) {
        viewModel.viewState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }.collectAsState(viewModel.viewState.value)

    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(16.dp)) {
        if (viewState.value.loading) {
            item("LOADING") {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.padding(16.dp))
                }
            }
        }

        if (viewState.value.error != null) {
            item("ERROR") {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.breed_list_error))
                }
            }
            return@LazyColumn
        }

        items(viewState.value.dogBreeds) { dogBreedItem ->
            DogBreedLayout(
                item = dogBreedItem,
                onClick = {
                    viewModel.onBreedClicked(dogBreedItem)
                },
                onSubBreedClick = { subBreedItem ->
                    viewModel.onSubBreedClicked(subBreedItem)
                }
            )
        }
    }
}

