package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationIii(
    var emerald: Emerald,
    @SerializedName("firered-leafgreen")
    var fireredLeafgreen: FireredLeafgreen,
    @SerializedName("ruby-sapphire")
    var rubySapphire: RubySapphire
)