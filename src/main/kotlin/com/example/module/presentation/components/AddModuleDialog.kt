package com.example.module.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import com.example.core.ui.colorDarkGreen
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
    val state: DialogState = rememberDialogState(width = 600.dp, height = 600.dp)

    DialogWindow(onCloseRequest = onDismissRequest,
        state = state,
        content = {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Insert New Module",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                Spacer(modifier = Modifier.height(16.dp))

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
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorDarkGreen),
                    border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                    onClick = { onSaveButtonClick() })
                {
                    Text(
                        text = "SAVE",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                }
            }
        })
}


