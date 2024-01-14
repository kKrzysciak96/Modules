package com.example.module.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.ui.colorDarkGreen

@Composable
fun DeleteDialog(
    delete: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onCloseRequest = { onDismiss() }) {
        Card {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Delete database?",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(modifier = Modifier.padding(20.dp)) {
                        OutlinedButton(
                            modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            onClick = {
                                delete()
                                onDismiss()
                            })
                        {
                            Text(
                                text = "Delete",
                                style = MaterialTheme.typography.h2
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorDarkGreen),
                            border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                            onClick = { onDismiss() })
                        {
                            Text(
                                text = "Back",
                                style = MaterialTheme.typography.h2
                            )
                        }
                    }
                }
            }
        }
    }
}