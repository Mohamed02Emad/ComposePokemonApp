package com.mo.composepokemonapp.presentation.pokemonList

import androidx.lifecycle.ViewModel
import com.mo.composepokemonapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    var flag = true


}