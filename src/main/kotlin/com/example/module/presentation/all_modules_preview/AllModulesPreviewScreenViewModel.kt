package com.example.module.presentation.all_modules_preview

import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.use_cases.ModuleUseCases
import com.example.module.presentation.utils.AllModulesPreviewScreenEvents
import com.example.module.presentation.utils.AllModulesPreviewState
import com.example.module.presentation.utils.PerformedActionMarker
import com.example.module.presentation.utils.UndoHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel


class AllModulesPreviewScreenViewModel(
    private val useCases: ModuleUseCases,
    private val undoHelper: UndoHelper
) : ViewModel() {
    val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow(AllModulesPreviewState(emptyList()))
    val state = _state.asStateFlow()

    private var oldModules: List<ModuleDomain> = emptyList()

    private var job: Job? = null

    init {
        getAllModuleNames()
    }

    fun onEvent(event: AllModulesPreviewScreenEvents) {
        when (event) {
            AllModulesPreviewScreenEvents.OnConfirmDeleteButtonClick -> {
                if (oldModules.isNotEmpty()) {
                    undoHelper.updateUndoList(oldModules.map {
                        Pair(PerformedActionMarker.ActionDeleted, it)
                    }
                    )
                }

                dropDatabase()
            }

            AllModulesPreviewScreenEvents.OnDeleteButtonClick -> {
                _state.value = state.value.copy(isDeleteAllDialogVisible = true)
            }

            AllModulesPreviewScreenEvents.OnDeleteDialogDismiss -> {
                _state.value = state.value.copy(isDeleteAllDialogVisible = false)
            }
        }
    }


    fun dropDatabase() {
        job = null
        job = viewModelScope.launch {

            useCases.dropDataBase()
        }
    }

    private fun getAllModuleNames() {
        viewModelScope.launch {
            useCases.getModulesUseCase().collect { modules ->
                oldModules = modules
                _state.value = state.value.copy(allModules = useCases.filterAllModuleNames(modules).sortedBy { it })
            }
        }
    }
}