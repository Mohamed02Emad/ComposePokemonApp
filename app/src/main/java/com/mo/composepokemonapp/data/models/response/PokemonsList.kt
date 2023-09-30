package com.mo.composepokemonapp.data.models.response

data class PokemonsList(
    var count: Int,
    var next: String,
    var previous: Any?,
    var results: List<Result>
):BaseResponse