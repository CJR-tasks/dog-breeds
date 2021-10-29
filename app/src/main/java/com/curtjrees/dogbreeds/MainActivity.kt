package com.curtjrees.dogbreeds

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.curtjrees.dogbreeds.features.breed_detail.DogBreedDetailFragment
import com.curtjrees.dogbreeds.features.breed_list.DogBreedListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeNavigationEvents()

        if (savedInstanceState == null) viewModel.initialNavigation()
    }

    private fun observeNavigationEvents() {
        viewModel.navigationEventFlow.flowWithLifecycle(lifecycle)
            .onEach(::handleNavigationEvent)
            .launchIn(lifecycleScope)
    }

    private fun handleNavigationEvent(event: NavigationEvent) {
        when (event) {
            is BreedListEvent -> switchFragment(DogBreedListFragment(), addToBackStack = false)
            is BreedDetailEvent -> switchFragment(DogBreedDetailFragment.newInstance(event.breed))
            is SubBreedDetailEvent -> TODO()
        }
    }

    private fun switchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.commit {
            if (addToBackStack) addToBackStack(fragment::class.qualifiedName)
            replace(R.id.fragmentContainerView, fragment)
        }
    }
}