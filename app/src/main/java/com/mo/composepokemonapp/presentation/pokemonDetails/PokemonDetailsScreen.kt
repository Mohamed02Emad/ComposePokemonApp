package com.mo.composepokemonapp.presentation.pokemonDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mo.composepokemonapp.data.api.ResponseState
import com.mo.composepokemonapp.data.models.response.Pokemon

@Composable
fun PokemonDetailScreen(
    dominantColor : Color,
    pokemonName : String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 250.dp,
    viewModel: PokemonDetailsViewModel
) {

    val pokemonInfo = produceState<ResponseState<Pokemon>>(initialValue = ResponseState.Loading()) {
        value = viewModel.getPokemonByName(pokemonName.lowercase())
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )

//        PokemonDetailStateWrapper(
//            pokemonInfo = pokemonInfo,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(
//                    top = topPadding + pokemonImageSize / 2f,
//                    start = 16.dp,
//                    end = 16.dp,
//                    bottom = 16.dp
//                )
//                .shadow(10.dp, RoundedCornerShape(10.dp))
//                .clip(RoundedCornerShape(10.dp))
//                .background(MaterialTheme.colors.surface)
//                .padding(16.dp)
//                .align(Alignment.BottomCenter),
//            loadingModifier = Modifier
//                .size(100.dp)
//                .align(Alignment.Center)
//                .padding(
//                    top = topPadding + pokemonImageSize / 2f,
//                    start = 16.dp,
//                    end = 16.dp,
//                    bottom = 16.dp
//                )
//        )
    }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (pokemonInfo is ResponseState.Success) {
            pokemonInfo.data?.sprites?.let {
                AsyncImage(
                    model = it.front_default,
                    contentDescription = null,
                    modifier = Modifier.size(pokemonImageSize),
                )
            }
        }else{
//            Text(
//                text = pokemonInfo.message.toString(),
//                color = Color.White
//            )
        }
    }

}



@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}


@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: ResponseState<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is ResponseState.Success -> {

        }
        is ResponseState.NetworkError ,
        is ResponseState.NotAuthorized ,
        is ResponseState.UnKnownError,
        is ResponseState.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is ResponseState.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
        is ResponseState.Empty -> {}
    }
}