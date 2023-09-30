package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class Other(
    var dream_world: DreamWorld,
    var home: Home,
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork
)