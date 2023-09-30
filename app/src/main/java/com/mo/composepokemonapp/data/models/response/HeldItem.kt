package com.mo.composepokemonapp.data.models.response

data class HeldItem(
    var item: Item,
    var version_details: List<VersionDetail>
)