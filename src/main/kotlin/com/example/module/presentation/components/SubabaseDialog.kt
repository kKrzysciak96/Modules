package com.example.module.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.rememberDialogState
import com.example.core.ui.colorDarkGreen


@Composable
fun SupaBaseDialog(
    confirm: () -> Unit,
    onDismiss: () -> Unit,
    question: String,
    confirmText: String,
    denyText: String,
    modifier: Modifier = Modifier
) {
    val state: DialogState = rememberDialogState(width = 600.dp, height = 600.dp)
    Dialog(onCloseRequest = { onDismiss() }, state = state) {
        Card {
            Box(contentAlignment = Alignment.Center, modifier = modifier.size(1000.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = question,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(modifier = Modifier.padding(20.dp)) {
                        OutlinedButton(
                            modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            onClick = {
                                confirm()
                                onDismiss()
                            })
                        {
                            Text(
                                text = confirmText,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorDarkGreen),
                            border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                            onClick = { onDismiss() })
                        {
                            Text(
                                text = denyText,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }
    }
}