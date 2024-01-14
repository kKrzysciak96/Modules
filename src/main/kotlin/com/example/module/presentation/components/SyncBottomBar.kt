package com.example.module.presentation.components


import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.ui.Purple40
import com.example.module.presentation.utils.MainScreenEvents

@Composable
fun SyncBottomBar(modifier: Modifier = Modifier, onEvent: (MainScreenEvents) -> Unit) {
    CustomBottomAppBar(
        modifier = modifier,
        containerColor = Purple40
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
//            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
            onClick = {
                onEvent(MainScreenEvents.OnFetchButtonClick)
            }) {
            Icon(imageVector = Icons.Default.Download, contentDescription = null)
        }
        IconButton(
            modifier = Modifier.weight(0.5f),
//            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
            onClick = {
                onEvent(MainScreenEvents.OnToggleBottomBar)
            }) {
            Icon(
                imageVector = Icons.Default.ToggleOn,
                contentDescription = null
            )
        }
        IconButton(
            modifier = Modifier.weight(0.5f),
//            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
            onClick = {
                onEvent(MainScreenEvents.OnPushButtonClick)

            }) {
            Icon(imageVector = Icons.Default.Upload, contentDescription = null)
        }
    }
}