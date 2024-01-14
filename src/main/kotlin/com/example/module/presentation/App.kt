package com.example.module.presentation

//import androidx.compose.runtime.Composable

//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.example.module.presentation.main_screen.MainScreen
import com.example.module.presentation.main_screen.MainScreenViewModel
import org.koin.java.KoinJavaComponent.inject


@Composable
@Preview
fun App() {

    val viewModel by inject<MainScreenViewModel>(MainScreenViewModel::class.java)

    MaterialTheme {
        MainScreen(viewModel = viewModel, snackBarHostState = SnackbarHostState(), onNextScreen = {})
    }
}