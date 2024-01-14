package com.example.module.presentation.main_screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: MainScreenViewModel,
    onNextScreen: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    state.currentPage?.let { currentPage ->
        val pagerState = rememberPagerState(
            initialPage = currentPage,
            initialPageOffsetFraction = 0f
        )

        if (state.isSearchActive) {
//        SearchView(
//            onEvent = viewModel::onEvent,
//            state = state,
//            uiEvent = viewModel.uiEvent,
//            snackBarHostState = snackBarHostState,
//
//            )
        } else {
            ModulesView(
                onEvent = viewModel::onEvent,
                state = state,
                uiEvent = viewModel.uiEvent,
                scope = scope,
                snackBarHostState = snackBarHostState,
                pagerState = pagerState,
                dropDatabase = viewModel::dropDatabase,
                isRedoButtonEnabled = viewModel::isRedoButtonEnabled,
                isUndoButtonEnabled = viewModel::isUndoButtonEnabled
            )
        }
    }
}




