package com.example.module.presentation.utils

import com.example.module.presentation.model.ModuleDisplayable

data class MainScreenState constructor(
    val modules: List<ModuleDisplayable> = emptyList(),
    val newModuleToInsert: ModuleDisplayable? = null,
    val isCalendarVisible: Boolean = false,
    val currentPage: Int? = null,
    val isSearchActive: Boolean = false,
    val searchedText: String = "",
    val searchOptions: SearchOptions,
    val isAddModuleDialogVisible: Boolean = false,
    val isSearchOptionsSectionVisible: Boolean = false,
    val isFetchDataDialogVisible: Boolean = false,
    val isPushDataDialogVisible: Boolean = false,
    val isApiRequestLoading: Boolean = false,
    val bottomBarState: Boolean = false,
)


