package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    var blackWhite: BlackWhite
)