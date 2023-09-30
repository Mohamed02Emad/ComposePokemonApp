package com.mo.composepokemonapp.utils.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoIconSearchBar(
    hint: String = "",
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                onSearch(newText)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black , fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 16.dp, vertical = 10.dp)

        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = MutableInteractionSource(),
                contentPadding = PaddingValues(0.dp),
                placeholder = { Text(hint, color = Color.LightGray , fontSize = 14.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }

}