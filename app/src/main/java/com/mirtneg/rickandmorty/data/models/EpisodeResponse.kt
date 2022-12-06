package com.mirtneg.rickandmorty.data.models

import com.google.gson.annotations.SerializedName

class EpisodeResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Episode>
) {
}