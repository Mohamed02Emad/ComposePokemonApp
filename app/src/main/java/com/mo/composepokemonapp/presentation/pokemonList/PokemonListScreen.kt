package com.mo.composepokemonapp.presentation.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mo.composepokemonapp.R
import com.mo.composepokemonapp.utils.composables.NoIconSearchBar

@Composable
fun PokemonListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = null,
                 modifier = Modifier
                     .fillMaxWidth()
                     .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            NoIconSearchBar(
                hint = "Search..." ,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ){

            }
        }
    }
}