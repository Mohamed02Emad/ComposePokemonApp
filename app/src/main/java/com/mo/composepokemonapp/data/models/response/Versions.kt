package com.mo.composepokemonapp.data.models.response

import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    var generation_i: GenerationI,
    @SerializedName("generation-ii")
    var generation_ii: GenerationIi,
    @SerializedName("generation-iii")
    var generation_iii: GenerationIii,
    @SerializedName("generation-iv")
    var generation_iv: GenerationIv,
    @SerializedName("generation-v")
    var generation_v: GenerationV,
    @SerializedName("generation-vi")
    var generation_vi: GenerationVi,
    @SerializedName("generation-vii")
    var generation_vii: GenerationVii,
    @SerializedName("generation-viii")
    var generation_viii: GenerationViii
)