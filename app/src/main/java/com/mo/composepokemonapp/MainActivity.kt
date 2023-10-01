package com.mo.composepokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mo.composepokemonapp.presentation.pokemonDetails.PokemonDetailScreen
import com.mo.composepokemonapp.presentation.pokemonDetails.PokemonDetailsViewModel
import com.mo.composepokemonapp.presentation.pokemonList.PokemonListScreen
import com.mo.composepokemonapp.presentation.pokemonList.PokemonListViewModel
import com.mo.composepokemonapp.ui.theme.ComposePokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pokemonListViewModel: PokemonListViewModel = ViewModelProvider(this@MainActivity)[PokemonListViewModel::class.java]
            val pokemonDetailsViewModel: PokemonDetailsViewModel = ViewModelProvider(this@MainActivity)[PokemonDetailsViewModel::class.java]

            ComposePokemonAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController , pokemonListViewModel)
                    }
                    composable(
                        "pokemon_details_screen/{dominateColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominateColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                        )
                    ) {
                       val dominateColor = remember {
                           val color = it.arguments?.getInt("dominateColor")
                           color?.let { Color(it) } ?: Color.White
                       }
                       val pokemonName = remember {
                           val name = it.arguments?.getString("pokemonName")
                           name ?: "Unknown"
                       }

                        PokemonDetailScreen(
                            dominantColor = dominateColor,
                            pokemonName = pokemonName,
                            navController = navController,
                            viewModel = pokemonDetailsViewModel
                        )

                    }
                }
            }
        }
    }
}

