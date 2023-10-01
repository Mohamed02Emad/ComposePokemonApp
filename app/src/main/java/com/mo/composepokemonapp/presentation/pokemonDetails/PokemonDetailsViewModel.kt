package com.mo.composepokemonapp.presentation.pokemonDetails

import androidx.lifecycle.ViewModel
import com.mo.composepokemonapp.data.api.ResponseState
import com.mo.composepokemonapp.data.models.response.Pokemon
import com.mo.composepokemonapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    suspend fun getPokemonByName(name:String) : ResponseState<Pokemon>{
        return repository.getPokemonByName(name)
    }

}