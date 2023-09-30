package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationI(
    @SerializedName("red-blue")
    var redBlue: RedBlue,
    var yellow: Yellow
)