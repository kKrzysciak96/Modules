package com.example.module.presentation.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.module.presentation.model.ModuleDisplayable
import com.example.module.presentation.utils.ModuleActions


@Composable
fun ModuleActionsMenu(
    module: ModuleDisplayable,
    onDismissRequest: () -> Unit,
    onActionDeleteClick: (ModuleDisplayable) -> Unit,
    onActionEditIncrementationClick: (ModuleDisplayable) -> Unit,
    onActionAddNewIncrementationClick: (ModuleDisplayable) -> Unit,
    onActionAddNewIncrementationFromDateClick: (ModuleDisplayable) -> Unit,
    onActionToggleSkippedClick: (ModuleDisplayable) -> Unit,
) {

    DropdownMenu(
        expanded = module.isModuleDropdownMenuVisible,
        onDismissRequest = { onDismissRequest() }) {
        ModuleActions.values().forEach { action ->
            DropdownMenuItem(
                onClick = {
                    when (action) {
                        ModuleActions.ActionDelete -> {
                            onActionDeleteClick(module)
                        }

                        ModuleActions.ActionEditIncrementation -> {
                            onActionEditIncrementationClick(module)
                        }

                        ModuleActions.ActionAddNewIncrementation -> {
                            onActionAddNewIncrementationClick(module)
                        }

                        ModuleActions.ActionAddNewIncrementationFromDate -> {
                            onActionAddNewIncrementationFromDateClick(module)
                        }

                        ModuleActions.ActionToggleSkipped -> {
                            onActionToggleSkippedClick(module)
                        }
                    }
                },
                content = { Text(text = action.actionName) })
        }
    }
}