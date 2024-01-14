package com.example.module.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.module.presentation.utils.SearchOptions
import com.example.module.presentation.utils.SearchOrder

@Composable
fun SearchOptionsSection(
    modifier: Modifier = Modifier,
    searchOptions: SearchOptions,
    onSearchOptionsChange: (SearchOptions) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DefaultRadioButton(
                text = "Exact",
                selected = searchOptions is SearchOptions.Exact,
                onSelect = { onSearchOptionsChange(SearchOptions.Exact(searchOptions.order)) }
            )

            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Contains",
                selected = searchOptions is SearchOptions.Contains,
                onSelect = { onSearchOptionsChange(SearchOptions.Contains(searchOptions.order)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = searchOptions.order is SearchOrder.Ascending,
                onSelect = { onSearchOptionsChange(searchOptions.copy(SearchOrder.Ascending)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = searchOptions.order is SearchOrder.Descending,
                onSelect = { onSearchOptionsChange(searchOptions.copy(SearchOrder.Descending)) })
        }
    }
}