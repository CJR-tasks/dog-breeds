package com.curtjrees.dogbreeds.features.subbreed_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.curtjrees.dogbreeds.R
import com.curtjrees.dogbreeds.common.ImagesListAdapter
import com.curtjrees.dogbreeds.databinding.FragmentDogBreedDetailBinding
import com.curtjrees.dogbreeds.entities.DogSubBreedItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DogSubBreedDetailFragment : Fragment() {

    private val viewModel: DogSubBreedDetailViewModel by viewModels()
    private var _binding: FragmentDogBreedDetailBinding? = null
    private val binding: FragmentDogBreedDetailBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = requireNotNull(requireArguments().getParcelable<DogSubBreedItem>(ITEM_ARG_KEY))
        viewModel.loadData(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentDogBreedDetailBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImagesListAdapter()
        setupRecyclerView(adapter)

        observeViewState(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(adapter: ImagesListAdapter) {
        with(binding.recyclerView) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewState(adapter: ImagesListAdapter) {
        viewModel.viewState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { render(it, adapter) }
            .launchIn(lifecycleScope)
    }

    private fun render(viewState: DogSubBreedDetailViewModel.ViewState, adapter: ImagesListAdapter) {
        binding.name.text = getString(R.string.sub_breed_name_template, viewState.dogSubBreed?.breedName?.capitalize().orEmpty(), viewState.dogSubBreed?.name?.capitalize().orEmpty())
        adapter.submitList(viewState.dogSubBreed?.images.orEmpty())
    }

    companion object {
        private const val ITEM_ARG_KEY = "ITEM_ARG_KEY"

        fun newInstance(breed: DogSubBreedItem): DogSubBreedDetailFragment = DogSubBreedDetailFragment().apply {
            arguments = bundleOf(ITEM_ARG_KEY to breed)
        }
    }

}

