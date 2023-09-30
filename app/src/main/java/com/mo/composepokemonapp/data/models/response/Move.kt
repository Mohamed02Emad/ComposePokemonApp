package com.mo.composepokemonapp.data.models.response

data class Move(
    var move: MoveX,
    var version_group_details: List<VersionGroupDetail>
)