package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationIv(
    @SerializedName("diamond-pearl")
    var diamondPearl: DiamondPearl,
    @SerializedName("heartgold-soulsilver")
    var heartgoldSoulsilver: HeartgoldSoulsilver,
    var platinum: Platinum
)