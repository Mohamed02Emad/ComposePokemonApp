package com.mo.composepokemonapp.presentation.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.mo.composepokemonapp.R
import com.mo.composepokemonapp.data.models.PokemonListEntry
import com.mo.composepokemonapp.ui.theme.RobotoCondensed
import com.mo.composepokemonapp.utils.calculateDominantColor
import com.mo.composepokemonapp.utils.composables.NoIconSearchBar

@Composable
fun PokemonListScreen(
    navController: NavController, viewModel: PokemonListViewModel
) {
    val context = LocalContext.current

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
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                viewModel.searchFor(it)
            }

            PokemonList(navController = navController, viewModel = viewModel)
        }
    }
}


@Composable
fun PokemonList(
    navController: NavController, viewModel: PokemonListViewModel
) {
    val pokemonList by remember { viewModel.pokemonsList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val searchQuery by remember { viewModel.searchQuery }

    val pokeList = viewModel.getDisplayList(searchQuery , pokemonList)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()

    ) {
        items(items = pokeList) {
            if (it == pokemonList.last() && endReached.not() && isLoading.not() && searchQuery.isEmpty()) {
                viewModel.loadPokemons()
            }
            PokemonCard(pokemon = it, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemons()
            }
        }
    }

}


@Composable
fun PokemonCard(
    pokemon: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

//    val defaultDominantColor = Color.White
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }


    Box(modifier = modifier
        .padding(bottom = 12.dp)
        .shadow(4.dp, RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .aspectRatio(1f)
        .background(
            Brush.verticalGradient(
                listOf(
                    dominantColor, defaultDominantColor
                )
            )
        )
        .clickable {
            navController.navigate(
                "pokemon_details_screen/${dominantColor.toArgb()}/${pokemon.name}"
            )
        }
        .padding(4.dp), contentAlignment = Center

    ) {
        Column(horizontalAlignment =CenterHorizontally) {

            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                onState = {
                    when (it) {
                        AsyncImagePainter.State.Empty -> {}
                        is AsyncImagePainter.State.Error -> {}
                        is AsyncImagePainter.State.Loading -> {}
                        is AsyncImagePainter.State.Success -> {
                            val painter = it.result.drawable
                            calculateDominantColor(painter) {color->
                                dominantColor = color
                            }
                        }
                    }
                },
            )

            Text(
                text = pokemon.name,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp,
                fontFamily = RobotoCondensed,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }

}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
