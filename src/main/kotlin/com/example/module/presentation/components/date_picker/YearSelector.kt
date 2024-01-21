package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YearSelector(
    year: Int,
    onValueChange: (Int) -> Unit,
    minYear: Int,
    maxYear: Int,
    modifier: Modifier = Modifier
) {
    var expandYearList by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { expandYearList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = year.toString())
        Spacer(Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)

        DropdownMenu(

            expanded = expandYearList,
            onDismissRequest = { expandYearList = false }
        ) {

            (minYear..maxYear).forEach { y ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(y)
                        expandYearList = false
                    },
                    text = {
                        Text(text = y.toString())
                    })
            }
        }
    }
}