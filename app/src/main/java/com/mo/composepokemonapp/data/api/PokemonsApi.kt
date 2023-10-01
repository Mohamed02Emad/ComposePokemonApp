package com.mo.composepokemonapp.data.api

import com.mo.composepokemonapp.data.models.response.Pokemon
import com.mo.composepokemonapp.data.models.response.PokemonsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonsApi  {
    
    @GET(POKEMONS_LIST)
    suspend fun getPokemonList(
         @Query("limit")
         limit: Int,
         @Query("offset")
         offset: Int,
    ): Response<PokemonsList>

    @GET
    suspend fun getPokemonByName(
        @Url url : String,
    ): Response<Pokemon>
}