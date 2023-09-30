package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class GenerationVii(
    var icons: Icons,
    @SerializedName("ultra-sun-ultra-moon")
    var ultraSunUltraMoon: UltraSunUltraMoon
)