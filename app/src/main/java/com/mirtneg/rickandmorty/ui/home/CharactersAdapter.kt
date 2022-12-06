package com.mirtneg.rickandmorty.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirtneg.rickandmorty.databinding.ListItemCharacterBinding
import com.mirtneg.rickandmorty.data.models.Character
import com.squareup.picasso.Picasso

class CharactersAdapter(val itemClick: (characterId : String) -> Unit) :
    RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    var characterItem : List<Character> = mutableListOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterItem.get(position)
        with(holder) {
            binding.nameTextView.text =  character.name
            binding.raceTextView.text = character.species
            Picasso.get().load(character.image).into(binding.characterImage)
        }
        holder.itemView.setOnClickListener {
            itemClick.invoke(character.id.toString())
        }
    }

    override fun getItemCount(): Int = characterItem.size
}