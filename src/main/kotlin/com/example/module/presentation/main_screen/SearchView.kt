package com.example.module.presentation.main_screen


import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.utils.UiEvent
import com.example.module.presentation.components.AddModuleDialog
import com.example.module.presentation.components.DayRow
import com.example.module.presentation.components.SearchOptionsSection
import com.example.module.presentation.components.date_picker.DatePickerDialog
import com.example.module.presentation.utils.MainScreenEvents
import com.example.module.presentation.utils.MainScreenState
import com.example.module.presentation.utils.SearchOptions
import com.example.module.presentation.utils.SearchOrder
import io.ktor.server.util.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*


@OptIn(InternalAPI::class)
@Composable
fun SearchView(
    onEvent: (MainScreenEvents) -> Unit,
    state: MainScreenState,
    uiEvent: Flow<UiEvent>,
    snackBarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { onEvent(MainScreenEvents.OnSearchViewClose) },
                    content = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                )
                IconButton(
                    onClick = { onEvent(MainScreenEvents.OnSearchOptionSectionToggle) },
                    content = { Icon(imageVector = Icons.Default.Sort, contentDescription = null) }
                )
            }
            AnimatedVisibility(
                visible = state.isSearchOptionsSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SearchOptionsSection(
                    modifier = Modifier.fillMaxWidth(),
                    searchOptions = state.searchOptions,
                    onSearchOptionsChange = { onEvent(MainScreenEvents.OnSearchOptionChange(it)) })
            }
            TextField(
                value = state.searchedText,
                onValueChange = { onEvent(MainScreenEvents.OnSearchedTextEntered(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter Text") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
//                    unfocusedContainerColor = Color.Gray,
//                    focusedContainerColor = Color.LightGray,
                    disabledPlaceholderColor = Color.White
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                val searchedModules = if (state.searchOptions is SearchOptions.Contains) {
                    state.modules.filter { it.name.contains(state.searchedText) }
                } else {
                    state.modules.filter { it.name == state.searchedText }
                }.run {
                    if (state.searchOptions.order is SearchOrder.Descending) {
                        sortedByDescending { it.epochDay }
                    } else {
                        sortedBy { it.epochDay }
                    }
                }
                val groupedModulesByDate = searchedModules.groupBy { it.epochDay }
                val epochKeys = groupedModulesByDate.keys.toList()
                items(epochKeys) { epochKey ->
                    val rowDate = LocalDate.ofEpochDay(epochKey)
                    DayRow(
                        date = rowDate,
                        modifier = Modifier
                            .fillMaxWidth(),
                        modules = groupedModulesByDate[epochKey]
                            ?.sortedByDescending { it.incrementation } ?: emptyList(),
                        onAddButtonClick = {
                            onEvent(MainScreenEvents.OnAddButtonClick(epochKey))
                        },
                        onDropdownMenuDismiss = { onEvent(MainScreenEvents.OnModuleActionsMenuDismiss) },
                        onAddNewIncrementationDropdownMenuDismiss = {
                            onEvent(
                                MainScreenEvents.OnAddNewIncrementDropDownMenuDismiss
                            )
                        },
                        onEditIncrementationDropdownMenuDismiss = {
                            onEvent(
                                MainScreenEvents.OnEditIncrementDropDownMenuDismiss
                            )
                        },
                        onLongPress = {
                            onEvent(
                                MainScreenEvents.OnLongModulePress(
                                    it
                                )
                            )
                        },
                        onEditIncrementation = { newModule, old ->
                            onEvent(
                                MainScreenEvents.OnEditIncrementation(
                                    newModule, old
                                )
                            )
                        },
                        onAddNewIncrementation = { newModule ->
                            onEvent(
                                MainScreenEvents.OnAddNewIncrementation(
                                    newModule
                                )
                            )
                        },
                        onResetNewIncrementation = { newModule, _ ->
                            onEvent(
                                MainScreenEvents.OnAddNewIncrementation(
                                    newModule
                                )
                            )
                        },
                        onModuleClick = { moduleId ->
                            onEvent(MainScreenEvents.OnModuleClick(moduleId))
                        },
                        onActionDeleteClick = { module ->
                            onEvent(MainScreenEvents.OnDeleteModuleClick(module))
                        },
                        onActionEditIncrementationClick = { module ->
                            onEvent(
                                MainScreenEvents.ActionEditIncrementation(
                                    module
                                )
                            )
                        },
                        onActionAddNewIncrementationClick = { module ->
                            onEvent(
                                MainScreenEvents.ActionAddNewIncrementation(
                                    module
                                )
                            )
                        },
                        onActionAddNewIncrementationFromDateClick = { module ->
                            onEvent(MainScreenEvents.OnModuleActionsMenuDismiss)
                            onEvent(
                                MainScreenEvents.ActionAddNewIncrementationFromDate(
                                    module
                                )
                            )
                        },
                        onActionToggleSkippedClick = { module ->
                            onEvent(MainScreenEvents.OnToggleSkipped(module))
                        }
                    )
                }
            }
        }
    }
//    CalendarDialog(
//        state = state.calendarState,
//        selection = CalendarSelection.Date(
//            onNegativeClick = {
//                onEvent(MainScreenEvents.OnCalendarDialogDismiss)
//            },
//            onSelectDate = { date ->
//                onEvent(MainScreenEvents.OnPickDate(date))
//            },
//            selectedDate = state.newModuleToInsert?.epochDay?.let { LocalDate.ofEpochDay(it) }
//        ),
//        config = CalendarConfig(
//            monthSelection = true,
//            yearSelection = true,
//        )
//    )
    if (state.isAddModuleDialogVisible) {
        state.newModuleToInsert?.let {
            AddModuleDialog(
                module = it,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                onNameTextEntered = { text ->
                    onEvent(MainScreenEvents.OnNameTextEntered(text))
                },
                onCommentTextEntered = { text ->
                    onEvent(MainScreenEvents.OnCommentTextEntered(text))
                },
                onIncrementationTextEntered = { number ->
                    onEvent(MainScreenEvents.OnIncrementationEntered(number))
                },
                onDismissRequest = {
                    onEvent(MainScreenEvents.OnAddModuleDialogDismiss)
                },
                onSaveButtonClick = {
                    onEvent(MainScreenEvents.OnSaveButtonClick(it))
                }
            )
        }
    }

    if (state.isCalendarVisible) {
        DatePickerDialog(
            initDate = Date(),
            onDismissRequest = { onEvent(MainScreenEvents.OnCalendarDialogDismiss) },
            onDateSelect = { date ->
                onEvent(MainScreenEvents.OnPickDate(date.toLocalDateTime().toLocalDate()))
            }
        )
    }
}