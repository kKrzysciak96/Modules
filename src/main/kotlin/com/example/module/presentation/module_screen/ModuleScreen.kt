package com.example.module.presentation.module_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.utils.UiEvent
import com.example.module.presentation.components.ModuleCommentTextRow
import com.example.module.presentation.components.ModuleNameTextRow
import com.example.module.presentation.utils.ModuleScreenEvents
import java.util.*

@Composable
fun ModuleScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: ModuleScreenViewModel,
    passedId: UUID,
    onBackPress: () -> Unit
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onIdPassed(passedId)
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.OnBack -> {
                    onBackPress()
                }

                is UiEvent.OnNextScreen -> {
                }

                is UiEvent.ShowSnackBar -> {
//                    snackBarHostState.currentSnackbarData?.dismiss()
//                    snackBarHostState.showSnackbar(
//                        message = event.message.asString(context),
//                        withDismissAction = true
//                    )
                }
            }
        }
    })

//    BackHandler {
//        viewModel.onEvent(ModuleScreenEvents.OnBackButtonPress)
//    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.value.module?.let { module ->
                ModuleNameTextRow(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Name",
                    text = module.name,
                    singleLine = true,
                    isIconEnabled = state.value.isNameEditEnabled,
                    onValueChange = { viewModel.onEvent(ModuleScreenEvents.OnNameEntered(it)) },
                    onIconClick = { viewModel.onEvent(ModuleScreenEvents.OnEditNameToggle) },

                    )
                Spacer(modifier = Modifier.height(16.dp))
                ModuleCommentTextRow(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Comment",
                    text = module.comment,
                    isIconEnabled = state.value.isCommentEditEnabled,
                    isColorDropdownMenuVisible = state.value.isColorDropdownMenuVisible,
                    onValueChange = { viewModel.onEvent(ModuleScreenEvents.OnCommentEntered(it)) },
                    onIconClick = { viewModel.onEvent(ModuleScreenEvents.OnEditCommentToggle) },
                    onColorButtonClick = { viewModel.onEvent(ModuleScreenEvents.OnToggleColorsClick) },
                    onColorMenuDismissRequest = { viewModel.onEvent(ModuleScreenEvents.OnColorMenuDismissRequest) }
                )
            }
        }

        FloatingActionButton(
            shape = RoundedCornerShape(100.dp),
            onClick = {
                state.value.module?.let { module ->
                    viewModel.onEvent(ModuleScreenEvents.OnModuleSaveButtonClick(module))
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
                modifier = Modifier
            )
        }

        FloatingActionButton(
            shape = RoundedCornerShape(100.dp),
            onClick = {
                viewModel.onEvent(ModuleScreenEvents.OnBackButtonPress)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}
