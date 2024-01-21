package com.example.module.presentation.all_modules_preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.module.presentation.components.DeleteDialog
import com.example.module.presentation.utils.AllModulesPreviewScreenEvents
import java.util.*


@Composable
fun AllModulesPreviewScreen(
    viewModel: AllModulesPreviewScreenViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Text(
                text = "All Modules Names",
                style = MaterialTheme.typography.h2
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 60.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.allModules) {
                    Text(text = it, fontSize = 32.sp)
                    Divider(color = Color.LightGray)
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            shape = RectangleShape,
            onClick = {
                viewModel.onEvent(AllModulesPreviewScreenEvents.OnDeleteButtonClick)
            }) {
            Text(text = "Delete".uppercase(Locale.ROOT))
        }

        if (state.isDeleteAllDialogVisible) {
            DeleteDialog(
                onDismiss = { viewModel.onEvent(AllModulesPreviewScreenEvents.OnDeleteDialogDismiss) },
                delete = { viewModel.onEvent(AllModulesPreviewScreenEvents.OnConfirmDeleteButtonClick) }
            )
        }
    }
}