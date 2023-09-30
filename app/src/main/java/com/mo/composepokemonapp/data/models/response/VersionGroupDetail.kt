package com.mo.composepokemonapp.data.models.response

data class VersionGroupDetail(
    var level_learned_at: Int,
    var move_learn_method: MoveLearnMethod,
    var version_group: VersionGroup
)