package com.mo.composepokemonapp.presentation.pokemonDetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.mo.composepokemonapp.R
import com.mo.composepokemonapp.data.api.ResponseState
import com.mo.composepokemonapp.data.models.response.Pokemon
import com.mo.composepokemonapp.utils.capitalizeFirst
import com.mo.composepokemonapp.utils.parseStatToAbbr
import com.mo.composepokemonapp.utils.parseStatToColor
import com.mo.composepokemonapp.utils.parseTypeToColor
import kotlin.math.roundToInt

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 70.dp,
    pokemonImageSize: Dp = 200.dp,
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

        PokemonDetailStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            topPadding = topPadding,
            pokemonImageSize = pokemonImageSize
        )
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize()

    ) {
        if (pokemonInfo is ResponseState.Success) {
            pokemonInfo.data?.sprites?.let {
                AsyncImage(
                    model = it.front_default,
                    contentDescription = null,
                    modifier = Modifier.size(pokemonImageSize)
                )
            }
        }
    }

}

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: ResponseState<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp
) {
    when (pokemonInfo) {
        is ResponseState.Success -> {
            PokemonInfo(
                pokemonInfo.data!!,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp, top = topPadding + pokemonImageSize / 2f)
                    .fillMaxSize()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.surface),
                pokemonImageSize = pokemonImageSize
            )
        }
        is ResponseState.NetworkError,
        is ResponseState.NotAuthorized,
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

@Composable
fun PokemonInfo(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    pokemonImageSize: Dp = 200.dp
) {
    val scrollState = rememberScrollState()
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp, top = pokemonImageSize / 2f - 10.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "#${pokemon.id} ${pokemon.name.capitalizeFirst()}",
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            ElementsSection(pokemon)
            PokemonPersonalInfo(pokemon)
            PokemonBasicStates(pokemon)
        }
    }
}

@Composable
fun PokemonBasicStates(
    pokemon: Pokemon
) {
    val maxBaseStat = remember {
        pokemon.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Base States",
            color = MaterialTheme.colors.onSecondary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        for (i in 0 until  pokemon.stats.size) {
            val state = pokemon.stats[i]
          Spacer(modifier = Modifier.height(8.dp))
          StateBar(
              stateName = parseStatToAbbr(state),
              stateValue = state.base_stat,
              stateMaxValue = maxBaseStat,
              stateColor = parseStatToColor(state),
              animDelay = i * 30,
              animDuration = 650,
              height = 28.dp
          )
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
            .padding(top = 30.dp)
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
fun ElementsSection(
    pokemon: Pokemon
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (type in pokemon.types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .heightIn(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalizeFirst(),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonPersonalInfo(pokemon: Pokemon) {

    val pokemonWeightInKg = remember {
        (pokemon.weight * 100f).roundToInt() / 1000f
    }

    val pokemonHeightInMeters = remember {
        (pokemon.height * 100f).roundToInt() / 1000f
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(top = 10.dp)
            .height(70.dp)
            .fillMaxWidth()
    ) {
        DetailCard(
            text = "${pokemonWeightInKg}kg",
            icon = painterResource(id = R.drawable.ic_weight)
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.LightGray)
        )
        DetailCard(
            text = "${pokemonHeightInMeters}m",
            icon = painterResource(id = R.drawable.ic_height)
        )
    }
}

@Composable
fun DetailCard(
    text: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary
        )

    }
}

@Composable
fun StateBar(
    stateName: String,
    stateValue: Int,
    stateMaxValue: Int,
    stateColor : Color,
    height : Dp = 28.dp ,
    animDuration : Int = 1000,
    animDelay : Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if(animationPlayed) {
            stateValue / stateMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(stateColor)
                .padding(horizontal = 8.dp)
        ){
            Text(
                text = stateName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * stateMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }

}