package com.example.module.presentation.module_screen

import com.example.core.utils.UiEvent
import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.use_cases.ModuleUseCases
import com.example.module.presentation.utils.ModuleScreenEvents
import com.example.module.presentation.utils.ModuleScreenState
import com.example.module.presentation.utils.PerformedActionMarker
import com.example.module.presentation.utils.UndoHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import java.util.*


class ModuleScreenViewModel(
    private val useCases: ModuleUseCases,
    private val undoHelper: UndoHelper
) : ViewModel() {

    val viewModelScope = CoroutineScope(Dispatchers.IO)

    private var oldModule: ModuleDomain? = null
    private var newModule: ModuleDomain? = null

    private val _state = MutableStateFlow(ModuleScreenState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var job: Job? = null
    fun onIdPassed(id: UUID) {
        if (state.value.module == null) {
            viewModelScope.launch {
                _state.value =
                    state.value.copy(module = useCases.getModuleUseCase(id).also { oldModule = it })
            }
        }
    }

    fun onEvent(event: ModuleScreenEvents) {
        when (event) {
            ModuleScreenEvents.OnBackButtonPress -> {

                newModule?.let {
                    if (newModule != oldModule) {
                        undoHelper.updateUndoList(
                            listOf(
                                Pair(
                                    PerformedActionMarker.ActionUpdated(oldModule!!),
                                    it
                                )
                            )
                        )
                    }
                }

                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(UiEvent.OnBack)
                }
            }

            is ModuleScreenEvents.OnCommentEntered -> {
                _state.value =
                    _state.value.copy(module = state.value.module?.copy(comment = event.comment))
            }

            ModuleScreenEvents.OnEditCommentToggle -> {
                _state.value =
                    _state.value.copy(isCommentEditEnabled = !state.value.isCommentEditEnabled)
            }

            ModuleScreenEvents.OnEditNameToggle -> {
                _state.value = _state.value.copy(isNameEditEnabled = !state.value.isNameEditEnabled)
            }

            is ModuleScreenEvents.OnModuleSaveButtonClick -> {
                _state.value = state.value.copy(
                    isCommentEditEnabled = false, isNameEditEnabled = false
                )
                update(event.module)
                newModule = event.module
//                job = null
//                job = viewModelScope.launch {
//                    _uiEvent.send(
//                        UiEvent.ShowSnackBar(
//                            UiText.StringResource(R.string.module_saved)
//                        )
//                    )
//                }
            }

            is ModuleScreenEvents.OnNameEntered -> {
                _state.value =
                    _state.value.copy(module = _state.value.module?.copy(name = event.name))
            }

            ModuleScreenEvents.OnToggleColorsClick -> {
                _state.value =
                    state.value.copy(isColorDropdownMenuVisible = !state.value.isColorDropdownMenuVisible)
            }

            ModuleScreenEvents.OnToggleFontSizeClick -> {
                _state.value =
                    state.value.copy(isTextDropdownMenuVisible = !state.value.isTextDropdownMenuVisible)
            }

            ModuleScreenEvents.OnColorMenuDismissRequest -> {
                _state.value = state.value.copy(isColorDropdownMenuVisible = false)
            }

            ModuleScreenEvents.OnFontSizeMenuDismissRequest -> {
                _state.value = state.value.copy(isTextDropdownMenuVisible = false)
            }
        }
    }

    private fun update(module: ModuleDomain) {
        job = null
        job = viewModelScope.launch {
            useCases.addModuleUseCase(module)

        }
    }
}