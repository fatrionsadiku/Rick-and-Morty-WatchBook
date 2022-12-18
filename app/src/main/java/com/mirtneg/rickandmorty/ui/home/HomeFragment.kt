package com.mirtneg.rickandmorty.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirtneg.rickandmorty.R
import com.mirtneg.rickandmorty.data.models.Character
import com.mirtneg.rickandmorty.data.models.Episode
import com.mirtneg.rickandmorty.databinding.DialogAdvancedFiltersBinding
import com.mirtneg.rickandmorty.databinding.FragmentHomeBinding
import com.mirtneg.rickandmorty.ui.characterdetail.EpisodesAdapter

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: CharactersAdapter
    lateinit var dialog: Dialog
    lateinit var dialogBinding: DialogAdvancedFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacters()

        setupFilterDialog()

        binding.filterButton.setOnClickListener {
            showDetailedFilterDialog()
        }

        binding.characterList.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CharactersAdapter(this::itemClick)
        binding.characterList.adapter = adapter

        viewModel.charactersList.observe(viewLifecycleOwner, Observer<List<Character>>() {
            adapter.characterItem = it
        })

        viewModel.filterResultsList.observe(viewLifecycleOwner, Observer<List<Character>>() {
            adapter.characterItem = it
        })

        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            viewModel.charactersList.value?.let { safeCharacter ->
                adapter.characterItem = safeCharacter.filter { character ->
                    character.name.startsWith(
                        text.toString(),
                        true
                    )
                }
            }
        }
    }

    private fun setupFilterDialog() {
        dialogBinding = DialogAdvancedFiltersBinding.inflate(requireActivity().layoutInflater)
        dialog = Dialog(requireActivity())
        dialog.setContentView(dialogBinding.root)

        dialog.window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
        }

        dialogBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.applyButton.setOnClickListener {

            if (viewModel.showingSearchResults) {
                viewModel.showingSearchResults = false
                viewModel.charactersList.value?.let {
                    adapter.characterItem = it
                }

                dialogBinding.specieEditText.setText("")
                dialogBinding.genderEditText.setText("")
                dialogBinding.statusEditText.setText("")
            } else {
                viewModel.filterCharacters(
                    dialogBinding.specieEditText.text.toString(),
                    dialogBinding.genderEditText.text.toString(),
                    dialogBinding.statusEditText.text.toString()
                )
            }
            dialog.dismiss()
        }
    }

    private fun showDetailedFilterDialog() {
        if (viewModel.showingSearchResults) {
            dialogBinding.applyButton.text = "Apply"
        } else {
            dialogBinding.applyButton.text = "Clear Filters"
        }
        dialog.show()
    }

    private fun itemClick(characterId: String) {
        val bundle = Bundle()
        bundle.putString("character_id", characterId)
        findNavController().navigate(R.id.action_homeFragment_to_characterDetailFragment, bundle)
    }
}