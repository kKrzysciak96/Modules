package com.example.module.presentation.main_screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.extensions.calculateDateUponGivenHorizontalPagerPage
import com.example.core.utils.UiEvent
import com.example.module.presentation.components.*
import com.example.module.presentation.utils.MainScreenEvents
import com.example.module.presentation.utils.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModulesView(
    onEvent: (MainScreenEvents) -> Unit,
    state: MainScreenState,
    uiEvent: Flow<UiEvent>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    pagerState: PagerState,
    dropDatabase: () -> Unit,
    isUndoButtonEnabled: () -> Boolean,
    isRedoButtonEnabled: () -> Boolean,
) {
    LaunchedEffect(key1 = pagerState.currentPage, block = {
        onEvent(MainScreenEvents.OnNewCardVisible(pagerState.currentPage))
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CardSelector(
                modifier = Modifier.fillMaxWidth(),
                onNextCardClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                onPreviousCardClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                onSearchClick = { onEvent(MainScreenEvents.OnSearchTextClick) },
                onShowAllTypeOfModules = { onEvent(MainScreenEvents.OnShowAllModulesPreviewIconClick) }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(bottom = 60.dp),
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
//                    snapVelocityThreshold = 800.dp,
//                    snapPositionalThreshold = 1f,
                    snapAnimationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) { page ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    val date = page.calculateDateUponGivenHorizontalPagerPage()
                    items(13) { days ->
                        val rowDate = date.plusDays(days.toLong())
                        val epochDate = rowDate.toEpochDay()
                        DayRow(
                            date = rowDate,
                            modifier = Modifier
                                .defaultMinSize(minHeight = 80.dp)
                                .fillMaxWidth(),
                            modules = state.modules
                                .filter { module -> module.epochDay == epochDate }
                                .sortedByDescending { it.incrementation.toInt() },
                            onAddButtonClick = {

                                onEvent(MainScreenEvents.OnAddButtonClick(epochDate))
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
                            onEditIncrementation = { newModule, oldModule ->
                                onEvent(
                                    MainScreenEvents.OnEditIncrementation(
                                        newModule, oldModule
                                    )
                                )
                            },
                            onAddNewIncrementation = { newModule ->

                                val onAddNewIncrementationFromDateCond =
                                    newModule.newIncrementation != null && state.newModuleToInsert != null
                                val onAddNewIncrementCond =
                                    newModule.newIncrementation != null && state.newModuleToInsert == null

                                when {
                                    onAddNewIncrementationFromDateCond -> {
                                        onEvent(
                                            MainScreenEvents.OnAddNewIncrementationFromDate(
                                                newModule
                                            )
                                        )
                                    }

                                    onAddNewIncrementCond -> {
                                        onEvent(
                                            MainScreenEvents.OnAddNewIncrementation(newModule)
                                        )
                                    }
                                }
                            },
                            onResetNewIncrementation = { newModule, oldModule ->
                                onEvent(
                                    MainScreenEvents.OnUpdateModule(
                                        newModule, oldModule
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
//                                state.calendarState.show()
                            },
                            onActionToggleSkippedClick = { module ->
                                onEvent(MainScreenEvents.OnToggleSkipped(module))
                            }
                        )
                    }
                }
            }
        }
//        CalendarDialog(
//            state = state.calendarState,
//            selection = CalendarSelection.Date(
//                onNegativeClick = {
//                    onEvent(MainScreenEvents.OnCalendarDialogDismiss)
//                },
//                onSelectDate = { date ->
//                    onEvent(MainScreenEvents.OnPickDate(date))
//                },
//                selectedDate = state.newModuleToInsert?.epochDay?.let { LocalDate.ofEpochDay(it) }
//            ),
//            config = CalendarConfig(
//                monthSelection = true,
//                yearSelection = true,
//            )
//        )
        if (state.isAddModuleDialogVisible) {
            state.newModuleToInsert?.let {
                AddModuleDialog(
                    module = it,
                    modifier = Modifier
                        .size(300.dp)
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

        if (state.bottomBarState) {
            SyncBottomBar(onEvent = onEvent, modifier = Modifier.align(Alignment.BottomCenter))
        } else {
            UndoBottomBar(
                onEvent = onEvent,
                modifier = Modifier.align(Alignment.BottomCenter),
                isRedoButtonEnabled = isRedoButtonEnabled,
                isUndoButtonEnabled = isUndoButtonEnabled
            )
        }

        if (state.isPushDataDialogVisible) {
            SupaBaseDialog(
                confirm = { onEvent(MainScreenEvents.OnConfirmPushClick) },
                onDismiss = { onEvent(MainScreenEvents.OnPushDialogDismiss) },
                question = "Push data to remote server?",
                confirmText = "Yes",
                denyText = "No",
            )
        }
        if (state.isFetchDataDialogVisible) {
            SupaBaseDialog(
                confirm = { onEvent(MainScreenEvents.OnConfirmFetchClick) },
                onDismiss = { onEvent(MainScreenEvents.OnFetchDialogDismiss) },
                question = "Fetch data from remote server?",
                confirmText = "Yes",
                denyText = "No",
            )
        }

        if (state.isApiRequestLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}