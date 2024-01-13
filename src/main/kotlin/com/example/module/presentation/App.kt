package com.example.module.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.module.presentation.main_screen.MainScreenViewModel
import com.example.module.presentation.utils.MainScreenEvents
import org.koin.java.KoinJavaComponent.inject

@Composable
@Preview
fun App() {

    val viewModel by inject<MainScreenViewModel>(MainScreenViewModel::class.java)
    val state = viewModel.state.collectAsState()
    LaunchedEffect(true) {

    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Button(onClick = {
                        viewModel.onEvent(MainScreenEvents.OnConfirmFetchClick)
                    }) {
                        Text("FETCH")
                    }
                }
                items(state.value.modules) { modules ->
                    Text(modules.name)
                }
            }
            if (state.value.isApiRequestLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}