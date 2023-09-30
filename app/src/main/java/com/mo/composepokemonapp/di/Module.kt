package com.mo.composepokemonapp.di

import com.mo.composepokemonapp.data.api.BASE_URL
import com.mo.composepokemonapp.data.api.PokemonsApi
import com.mo.composepokemonapp.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokemonsApi
    ) = PokemonRepository(api)
    @Singleton
    @Provides
    fun providePokemonsApi(): PokemonsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonsApi::class.java)
    }
}