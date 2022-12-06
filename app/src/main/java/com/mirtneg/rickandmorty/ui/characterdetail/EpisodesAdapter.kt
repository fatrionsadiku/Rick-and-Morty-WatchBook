package com.mirtneg.rickandmorty.ui.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirtneg.rickandmorty.data.models.Episode
import com.mirtneg.rickandmorty.databinding.ItemEpisodesBinding


class EpisodesAdapter(val itemClick: (episodeId: String) -> Unit) :
    RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {
    var episodes: List<Episode> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ItemEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes[position]
        with(holder) {
            binding.episodeNameTextView.text = episode.name
            binding.episodeCountTextView.text = episode.episode
            binding.episodeDateTextView.text = episode.airDate
        }
        holder.itemView.setOnClickListener {
            itemClick.invoke(episode.id.toString())
        }
    }

    override fun getItemCount(): Int = episodes.size
}
