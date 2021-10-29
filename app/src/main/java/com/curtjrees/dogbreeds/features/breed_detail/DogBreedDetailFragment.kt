package com.curtjrees.dogbreeds.features.breed_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.load
import com.curtjrees.dogbreeds.databinding.FragmentDogBreedDetailBinding
import com.curtjrees.dogbreeds.entities.DogBreedItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DogBreedDetailFragment : Fragment() {

    private val viewModel: DogBreedDetailViewModel by viewModels()
    private var _binding: FragmentDogBreedDetailBinding? = null
    private val binding: FragmentDogBreedDetailBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = requireNotNull(requireArguments().getParcelable<DogBreedItem>(ITEM_ARG_KEY))
        viewModel.loadData(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentDogBreedDetailBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewState() {
        viewModel.viewState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(viewState: DogBreedDetailViewModel.ViewState) {
        binding.name.text = viewState.dogBreed?.name
        binding.heroImage.load(viewState.dogBreed?.images?.firstOrNull())
    }

    companion object {
        private const val ITEM_ARG_KEY = "ITEM_ARG_KEY"

        fun newInstance(breed: DogBreedItem): DogBreedDetailFragment = DogBreedDetailFragment().apply {
            arguments = bundleOf(ITEM_ARG_KEY to breed)
        }
    }

}

