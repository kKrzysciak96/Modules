package com.example.module.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.module.presentation.model.ModuleDisplayable

@Composable
fun AddModuleDialog(
    modifier: Modifier = Modifier,
    module: ModuleDisplayable,
    onDismissRequest: () -> Unit,
    onSaveButtonClick: () -> Unit,
    onNameTextEntered: (String) -> Unit,
    onCommentTextEntered: (String) -> Unit,
    onIncrementationTextEntered: (String) -> Unit,

    ) {
    Dialog(onCloseRequest = onDismissRequest) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CustomIcon(
                imageVector = Icons.Default.Save,
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onSaveButtonClick
            )
            CustomIcon(
                imageVector = Icons.Default.Close,
                modifier = Modifier.align(Alignment.TopStart),
                onClick = onDismissRequest
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                CustomModuleDialogRow(
                    label = "Name",
                    text = module.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    onValueChange = onNameTextEntered,
                    placeholderText = "Enter Name",
                    singleLine = true
                )
                CustomModuleDialogRow(
                    label = "Comment",
                    text = module.comment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    onValueChange = onCommentTextEntered,
                    placeholderText = "Enter Comment"
                )
                CustomModuleDialogRow(
                    label = "Incrementation",
                    text = module.incrementation.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    onValueChange = onIncrementationTextEntered,
                    placeholderText = "Enter Incrementation",
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }
    }
}


