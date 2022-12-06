package com.mirtneg.rickandmorty.ui.episodedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirtneg.rickandmorty.R
import com.mirtneg.rickandmorty.data.models.Character
import com.mirtneg.rickandmorty.databinding.FragmentEpisodeDetailBinding
import com.mirtneg.rickandmorty.ui.home.CharactersAdapter

class EpisodeDetailFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeDetailBinding
    private lateinit var viewModel: EpisodeDetailViewModel
    private lateinit var adapter: CharactersAdapter

    val args by navArgs<EpisodeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[EpisodeDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEpisodeById(args.episodeId)


        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CharactersAdapter(this::onEpisodeClick)
        binding.characterRecyclerView.adapter = adapter

        viewModel.characterResponse.observe(viewLifecycleOwner) {
            adapter.characterItem = it
        }

        viewModel.episodeResponse.observe(viewLifecycleOwner) {
            with(it) {

                binding.episodeTitle.text = name

                binding.episodeInfo.tagTextView.text = "Episode"
                binding.episodeInfo.dataTextView.text = episode

                binding.airDate.tagTextView.text = "Date"
                binding.airDate.dataTextView.text = airDate
            }
        }


    }

    fun onEpisodeClick(characterId: String) {
        val bundle = Bundle()
        bundle.putString("character_id", characterId)
        findNavController().navigate(
            R.id.action_episodeDetailFragment_to_characterDetailFragment,
            bundle
        )

    }
}