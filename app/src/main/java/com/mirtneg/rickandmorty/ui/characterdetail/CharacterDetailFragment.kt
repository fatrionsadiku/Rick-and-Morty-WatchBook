package com.mirtneg.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.mirtneg.rickandmorty.R

import com.mirtneg.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.squareup.picasso.Picasso


class CharacterDetailFragment : Fragment() {
    lateinit var viewModel: CharacterDetailViewModel
    private lateinit var binding: FragmentCharacterDetailBinding
    private lateinit var adapter: EpisodesAdapter
    private val args by navArgs<CharacterDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterDetailViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EpisodesAdapter(this::onEpisodeClick)
        viewModel.getCharacterById(args.characterId)
        binding.episodesRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.episodesRecyclerView.adapter = adapter
        viewModel.characterResponse.observe(viewLifecycleOwner) {
            with(it) {
                binding.characterName.text = name
                Picasso.get().load(image).into(binding.characterImage)

                binding.genderInfo.tagTextView.text = "Gender"
                binding.genderInfo.dataTextView.text = gender

                binding.statusInfo.tagTextView.text = "Status"
                binding.statusInfo.dataTextView.text = status

                binding.speciesInfo.tagTextView.text = "Species"
                binding.speciesInfo.dataTextView.text = species

                binding.originInfo.tagTextView.text = "Origin"
                binding.originInfo.dataTextView.text = origin.name

                binding.locationInfo.tagTextView.text = "Location"
                binding.locationInfo.dataTextView.text = location.name

                binding.typeInfo.tagTextView.text = "Type"
                binding.typeInfo.dataTextView.text = type
            }
        }

        viewModel.episodeResponse.observe(viewLifecycleOwner) {
            adapter.episodes = it
        }

        binding.backButton.setOnClickListener() {

        }


    }

    private fun onEpisodeClick(episodeId: String) {
       val action = CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEpisodeDetailFragment(episodeId)
        findNavController().navigate(action)
    }
}