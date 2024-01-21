package com.example.module.presentation.components.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.clickable { expandYearList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = year.toString())
        Spacer(Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)

        DropdownMenu(
            scrollState = scrollState,
            expanded = expandYearList,
            onDismissRequest = { expandYearList = false }
        ) {

            (minYear..maxYear).forEach { y ->
                DropdownMenuItem(onClick = {
                    onValueChange(y)
                    expandYearList = false
                }) {
                    Text(text = y.toString())
                }
            }
        }
    }
}