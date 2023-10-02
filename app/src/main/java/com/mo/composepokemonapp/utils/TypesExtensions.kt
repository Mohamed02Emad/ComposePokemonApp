package com.mo.composepokemonapp.utils

fun String.capitalizeFirst(): String = this.replaceFirstChar { it.uppercase() }
fun String.lowerFirst(): String = this.replaceFirstChar { it.lowercase() }