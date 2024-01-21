package com.example.module.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = 4.dp,
    contentPadding: PaddingValues = PaddingValues(8.dp),

    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shape = RectangleShape,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(contentPadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

