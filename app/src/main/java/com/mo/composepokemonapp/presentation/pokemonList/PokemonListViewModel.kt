package com.mo.composepokemonapp.presentation.pokemonList

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mo.composepokemonapp.data.api.PAGE_SIZE
import com.mo.composepokemonapp.data.api.ResponseState
import com.mo.composepokemonapp.data.models.PokemonListEntry
import com.mo.composepokemonapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private var currentPage = 0

    var pokemonsList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var endReached = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")


    init {
        loadPokemons()
    }
    fun loadPokemons() {
        viewModelScope.launch {
            val response = repository.getPokemonList(currentPage * PAGE_SIZE, PAGE_SIZE)
            when(response){
                is ResponseState.Loading,
                is ResponseState.Empty,
                is ResponseState.NotAuthorized ,
                is ResponseState.Error ,
                is ResponseState.NetworkError ,
                is ResponseState.UnKnownError -> {
                    loadError.value = response.message ?: "Error"
                    isLoading.value = false
                }
                is ResponseState.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= response.data!!.count
                    val pokedexEntries = response.data!!.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonListEntry(entry.name.uppercase(Locale.ROOT), url, number.toInt())
                    }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonsList.value += pokedexEntries
                }
            }
        }
    }


}