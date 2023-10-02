package com.mo.composepokemonapp.utils

import androidx.compose.ui.graphics.Color
import com.mo.composepokemonapp.data.models.response.Stat
import com.mo.composepokemonapp.data.models.response.Type
import com.mo.composepokemonapp.ui.theme.AtkColor
import com.mo.composepokemonapp.ui.theme.DefColor
import com.mo.composepokemonapp.ui.theme.HPColor
import com.mo.composepokemonapp.ui.theme.SpAtkColor
import com.mo.composepokemonapp.ui.theme.SpDefColor
import com.mo.composepokemonapp.ui.theme.SpdColor
import com.mo.composepokemonapp.ui.theme.TypeBug
import com.mo.composepokemonapp.ui.theme.TypeDark
import com.mo.composepokemonapp.ui.theme.TypeDragon
import com.mo.composepokemonapp.ui.theme.TypeElectric
import com.mo.composepokemonapp.ui.theme.TypeFairy
import com.mo.composepokemonapp.ui.theme.TypeFighting
import com.mo.composepokemonapp.ui.theme.TypeFire
import com.mo.composepokemonapp.ui.theme.TypeFlying
import com.mo.composepokemonapp.ui.theme.TypeGhost
import com.mo.composepokemonapp.ui.theme.TypeGrass
import com.mo.composepokemonapp.ui.theme.TypeGround
import com.mo.composepokemonapp.ui.theme.TypeIce
import com.mo.composepokemonapp.ui.theme.TypeNormal
import com.mo.composepokemonapp.ui.theme.TypePoison
import com.mo.composepokemonapp.ui.theme.TypePsychic
import com.mo.composepokemonapp.ui.theme.TypeRock
import com.mo.composepokemonapp.ui.theme.TypeSteel
import com.mo.composepokemonapp.ui.theme.TypeWater

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.lowercase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}