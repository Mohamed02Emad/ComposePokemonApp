package com.mo.composepokemonapp.data.repository

import android.util.Log
import com.mo.composepokemonapp.data.api.BASE_URL
import com.mo.composepokemonapp.data.api.GET_A_POKEMON
import com.mo.composepokemonapp.data.api.PokemonsApi
import com.mo.composepokemonapp.data.api.ResponseState
import com.mo.composepokemonapp.data.api.handleResponse
import com.mo.composepokemonapp.data.models.response.Pokemon
import com.mo.composepokemonapp.data.models.response.PokemonsList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.math.log

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokemonsApi
) {
    suspend fun getPokemonList(offset: Int, limit: Int): ResponseState<PokemonsList> {
        val response = try {
            api.getPokemonList(
                limit = limit,
                offset = offset
            )
        } catch (_: Exception) {
            null
        }
        return handleResponse(response)
    }

    suspend fun getPokemonByName(pokemonName: String): ResponseState<Pokemon> {
        val response = try {
            val url ="$BASE_URL$GET_A_POKEMON$pokemonName"
            api.getPokemonByName(
                url = url
            )
        } catch (e: Exception) {
            null
        }
        Log.d("mohamed", response?.code().toString())
        return handleResponse(response)
    }
}