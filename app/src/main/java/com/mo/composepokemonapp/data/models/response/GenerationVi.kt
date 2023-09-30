package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationVi(
    @SerializedName("omegaruby-alphasapphire")
    var omegarubyAlphasapphire: OmegarubyAlphasapphire,
    @SerializedName("x-y")
    var xy: XY
)