package com.example.module.presentation.main_screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import com.example.core.utils.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: MainScreenViewModel,
    onNextScreen: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.OnNextScreen -> {
                        onNextScreen(event.route)
                    }

                    is UiEvent.ShowSnackBar -> {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            event.message.asString(),
//                            withDismissAction = true
                        )
                    }

                    UiEvent.OnBack -> {
                    }
                }
            }
        })

    state.currentPage?.let { currentPage ->
        val pagerState = rememberPagerState(
            initialPage = currentPage,
            initialPageOffsetFraction = 0f
        ) {
            Int.MAX_VALUE
        }

        if (state.isSearchActive) {
            SearchView(
                onEvent = viewModel::onEvent,
                state = state,
                uiEvent = viewModel.uiEvent,

                )
        } else {
            ModulesView(
                onEvent = viewModel::onEvent,
                state = state,
                uiEvent = viewModel.uiEvent,
                scope = scope,
                pagerState = pagerState,
                dropDatabase = viewModel::dropDatabase,
                isRedoButtonEnabled = viewModel::isRedoButtonEnabled,
                isUndoButtonEnabled = viewModel::isUndoButtonEnabled
            )
        }
    }
}




