package com.example.module.presentation.main_screen


import com.example.core.extensions.isDigitsOnly
import com.example.core.utils.ApiResult
import com.example.core.utils.UiEvent
import com.example.core.utils.UiText
import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.use_cases.ModuleUseCases
import com.example.module.presentation.model.ModuleDisplayable
import com.example.module.presentation.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*


class MainScreenViewModel(
    private val useCases: ModuleUseCases,
    private val undoHelper: UndoHelper
) {
    val viewModelScope = CoroutineScope(Dispatchers.IO)


    private val _state = MutableStateFlow(
        MainScreenState(
//            calendarState = UseCaseState(
//                onDismissRequest = { onEvent(MainScreenEvents.OnCalendarDialogDismiss) },
//            ),
            searchOptions = SearchOptions.Contains(SearchOrder.Descending)
        )
    )
    val state = _state.asStateFlow()

    private var job: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadLastCard()
        getModules()
    }

    private fun loadLastCard() {
        job = viewModelScope.launch {
            val lastPage = useCases.loadLastCardUseCase()
            println(lastPage.toString() + "load")
            _state.value = state.value.copy(currentPage = lastPage)
        }
    }

    fun onEvent(event: MainScreenEvents) {

        when (event) {

            is MainScreenEvents.OnCalendarDialogDismiss -> {
                _state.value = state.value.copy(newModuleToInsert = null)
            }

            is MainScreenEvents.OnAddButtonClick -> {
                val module = ModuleDisplayable(
                    name = "",
                    comment = "",
                    incrementation = "",
                    epochDay = event.epochDate,
                    id = UUID.randomUUID(),
                    timeStamp = System.currentTimeMillis()
                )
                _state.value = state.value.copy(newModuleToInsert = module)
                _state.value = state.value.copy(isAddModuleDialogVisible = true)
            }

            MainScreenEvents.OnAddModuleDialogDismiss -> {
                _state.value = state.value.copy(newModuleToInsert = null)
                _state.value = state.value.copy(isAddModuleDialogVisible = false)
            }

            is MainScreenEvents.OnSaveButtonClick -> {
                val module = event.module
                try {
                    if (module.name.isBlank()) {
//                        throw CustomException(uiTextMessage = UiText.StringResource(R.string.name_error))
                    }
                    if (!module.incrementation.isDigitsOnly() || module.incrementation.isEmpty()) {

//                        throw CustomException(uiTextMessage = UiText.StringResource(R.string.number_error))
                    }
                    onEvent(MainScreenEvents.OnAddModuleDialogDismiss)
                    job = null
                    job = viewModelScope.launch {
                        addModule(event.module)
                        undoHelper.updateUndoList(
                            listOf(
                                Pair(
                                    PerformedActionMarker.ActionAdded,
                                    event.module.toModuleDomain()
                                )
                            )
                        )

                    }
                } catch (e: CustomException) {
                    job = null
                    job = viewModelScope.launch {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                message = e.uiTextMessage
                            )
                        )
                    }
                }
            }

            is MainScreenEvents.OnDeleteModuleClick -> {
                deleteModule(event.module)
                undoHelper.updateUndoList(
                    listOf(
                        Pair(
                            PerformedActionMarker.ActionDeleted,
                            event.module.toModuleDomain()
                        )
                    )
                )
            }

            is MainScreenEvents.OnCommentTextEntered -> {
                _state.value =
                    state.value.copy(newModuleToInsert = state.value.newModuleToInsert?.copy(comment = event.comment))
            }

            is MainScreenEvents.OnIncrementationEntered -> {
                _state.value = state.value.copy(
                    newModuleToInsert = state.value.newModuleToInsert?.copy(incrementation = event.incrementationNumber)
                )
            }

            is MainScreenEvents.OnNameTextEntered -> {
                _state.value =
                    state.value.copy(newModuleToInsert = state.value.newModuleToInsert?.copy(name = event.name))
            }

            is MainScreenEvents.OnLongModulePress -> {
                _state.value = state.value.copy(modules = state.value.modules.map {
                    if (it.id == event.moduleId) {
                        it.copy(isModuleDropdownMenuVisible = true)
                    } else {
                        it
                    }
                })
            }

            MainScreenEvents.OnModuleActionsMenuDismiss -> {
                _state.value = state.value.copy(modules = state.value.modules.map {
                    it.copy(isModuleDropdownMenuVisible = false)
                })
            }

            is MainScreenEvents.ActionAddNewIncrementation -> {
                _state.value = state.value.copy(modules = state.value.modules.map {
                    it.copy(isModuleDropdownMenuVisible = false)
                })

                _state.value = state.value.copy(modules = state.value.modules.map {
                    if (it.id == event.module.id) {
                        it.copy(isAddNewIncrementDropdownMenuVisible = true)
                    } else {
                        it
                    }
                })
            }

            MainScreenEvents.OnAddNewIncrementDropDownMenuDismiss -> {
                _state.value = state.value.copy(
                    modules = state.value.modules.map {
                        it.copy(
                            isAddNewIncrementDropdownMenuVisible = false,
                        )
                    }
                )
            }

            MainScreenEvents.OnEditIncrementDropDownMenuDismiss -> {
                _state.value = state.value.copy(
                    modules = state.value.modules.map {
                        it.copy(
                            isEditIncrementDropdownMenuVisible = false,
                        )
                    }
                )
            }

            is MainScreenEvents.ActionEditIncrementation -> {
                _state.value = state.value.copy(modules = state.value.modules.map {
                    it.copy(isModuleDropdownMenuVisible = false)
                })
                _state.value = state.value.copy(
                    modules = state.value.modules.map {
                        if (it.id == event.module.id) {
                            it.copy(isEditIncrementDropdownMenuVisible = true)
                        } else {
                            it
                        }
                    }
                )
            }

            is MainScreenEvents.OnEditIncrementation -> {
                job = null
                job = viewModelScope.launch {

                    addModule(event.newModule)
                    undoHelper.updateUndoList(
                        listOf(
                            Pair(
                                PerformedActionMarker.ActionUpdated(event.oldModule.toModuleDomain()),
                                event.newModule.toModuleDomain()
                            )
                        )
                    )
                }
            }

            is MainScreenEvents.OnUpdateModule -> {
                job = null
                job = viewModelScope.launch {
                    onUpdateModule(event.newModule, event.oldModule)
                }
            }

            is MainScreenEvents.OnAddNewIncrementationFromDate -> {
                job = null
                job = viewModelScope.launch {
                    onAddNewIncrementationFromDate(event.module, state.value.newModuleToInsert!!)
                }
            }

            is MainScreenEvents.OnAddNewIncrementation -> {
                job = null
                job = viewModelScope.launch {
                    onAddNewIncrementation(event.module)
                }
            }

            is MainScreenEvents.OnPickDate -> {

                _state.value = state.value.copy(
                    newModuleToInsert = state.value.newModuleToInsert?.copy(epochDay = event.date.toEpochDay())
                )

                _state.value = state.value.copy(modules = state.value.modules.map {
                    if (it.id == state.value.newModuleToInsert?.id) {
                        it.copy(isAddNewIncrementDropdownMenuVisible = true)
                    } else {
                        it
                    }
                })
            }

            is MainScreenEvents.ActionAddNewIncrementationFromDate -> {
                _state.value = state.value.copy(newModuleToInsert = event.module)
            }

            is MainScreenEvents.OnToggleSkipped -> {
                job = null
                job = viewModelScope.launch {
                    val module = event.module.copy(isSkipped = !event.module.isSkipped)
                    addModule(module)
                    undoHelper.updateUndoList(
                        listOf(
                            Pair(
                                PerformedActionMarker.ActionUpdated(
                                    module.toModuleDomain().copy(
                                        isSkipped = !module.isSkipped
                                    )
                                ), module.toModuleDomain()
                            )
                        )
                    )
                }
            }

            is MainScreenEvents.OnModuleClick -> {
                job = null
                job = viewModelScope.launch {
//                    val route = Routes.MODULE_SCREEN + "/${event.id}"
//                    _uiEvent.send(UiEvent.OnNextScreen(route))
                }
            }

            MainScreenEvents.OnSearchTextClick -> {

                _state.value = state.value.copy(isSearchActive = true)
            }

            MainScreenEvents.OnSearchViewClose -> {
                _state.value = state.value.copy(isSearchActive = false)
            }

            is MainScreenEvents.OnSearchedTextEntered -> {
                _state.value = state.value.copy(searchedText = event.text)
            }

            is MainScreenEvents.OnSearchOptionChange -> {
                _state.value = state.value.copy(searchOptions = event.searchOption)
            }

            is MainScreenEvents.OnSearchOptionSectionToggle -> {
                _state.value =
                    state.value.copy(isSearchOptionsSectionVisible = !state.value.isSearchOptionsSectionVisible)
            }

            MainScreenEvents.OnConfirmFetchClick -> {
                job = null
                job = viewModelScope.launch {
                    fetchModulesFromRemote().collect { apiResult ->
                        when (apiResult) {
                            is ApiResult.Error -> {
                                _state.value = state.value.copy(isApiRequestLoading = false)
                                apiResult.message?.let {
                                    _uiEvent.send(
                                        UiEvent.ShowSnackBar(
                                            UiText.DynamicString(
                                                it
                                            )
                                        )
                                    )
                                }
                            }

                            ApiResult.Loading -> {
                                _state.value = state.value.copy(isApiRequestLoading = true)
                            }

                            is ApiResult.Success<*> -> {
                                _state.value =
                                    state.value.copy(modules = (apiResult as ApiResult.Success<List<ModuleDomain>>).data.map {
                                        ModuleDisplayable(it)
                                    }, isApiRequestLoading = false)
//                                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.fetch_success)))
                            }
                        }
                    }
                }
            }

            MainScreenEvents.OnConfirmPushClick -> {
                job = null
                job = viewModelScope.launch {
                    pushModulesToRemote().collect { apiResult ->
                        when (apiResult) {
                            is ApiResult.Error -> {
                                _state.value = state.value.copy(isApiRequestLoading = false)
                                apiResult.message?.let {
                                    _uiEvent.send(
                                        UiEvent.ShowSnackBar(
                                            UiText.DynamicString(
                                                it
                                            )
                                        )
                                    )
                                }
                            }

                            ApiResult.Loading -> {
                                _state.value = state.value.copy(isApiRequestLoading = true)
                            }

                            is ApiResult.Success<*> -> {
                                _state.value = state.value.copy(isApiRequestLoading = false)
//                                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.push_success)))
                            }
                        }
                    }
                }
            }

            is MainScreenEvents.OnNewCardVisible -> {
                updateLastCard(event.cardNumber)
            }

            is MainScreenEvents.OnShowAllModulesPreviewIconClick -> {
                job = null
                job = viewModelScope.launch {
//                    val route = Routes.ALL_MODULES_PREVIEW_SCREEN
//                    _uiEvent.send(UiEvent.OnNextScreen(route))
                }
            }

            MainScreenEvents.OnFetchButtonClick -> {
                _state.value = state.value.copy(isFetchDataDialogVisible = true)
            }

            MainScreenEvents.OnFetchDialogDismiss -> {
                _state.value = state.value.copy(isFetchDataDialogVisible = false)
            }

            MainScreenEvents.OnPushButtonClick -> {
                _state.value = state.value.copy(isPushDataDialogVisible = true)
            }

            MainScreenEvents.OnPushDialogDismiss -> {
                _state.value = state.value.copy(isPushDataDialogVisible = false)
            }

            MainScreenEvents.OnRedoClick -> {

                undoHelper.undoIndex = undoHelper.undoIndex?.plus(1) ?: 0
                undoHelper.undoIndex?.let { listIndexToAdd ->
                    undoHelper.undoList.getOrNull(listIndexToAdd)?.let { listOfPerformedActions ->
                        job = null
                        job = viewModelScope.launch {
                            listOfPerformedActions.forEach {
                                when (it.first) {
                                    PerformedActionMarker.ActionAdded -> {
                                        addModule(ModuleDisplayable(it.second))
                                    }

                                    PerformedActionMarker.ActionDeleted -> {
                                        deleteModule(ModuleDisplayable(it.second))
                                    }

                                    is PerformedActionMarker.ActionUpdated -> {
                                        addModule(ModuleDisplayable(it.second))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            MainScreenEvents.OnUndoClick -> {

                undoHelper.undoIndex?.let { listIndexToDelete ->
                    undoHelper.undoList.getOrNull(listIndexToDelete)
                        ?.let { listOfPerformedActions ->
                            job = null
                            job = viewModelScope.launch {
                                listOfPerformedActions.forEach {
                                    when (val action = it.first) {
                                        PerformedActionMarker.ActionAdded -> {
                                            deleteModule(ModuleDisplayable(it.second))
                                        }

                                        PerformedActionMarker.ActionDeleted -> {
                                            addModule(ModuleDisplayable(it.second))
                                        }

                                        is PerformedActionMarker.ActionUpdated -> {
                                            addModule(ModuleDisplayable(action.oldModule))
                                        }
                                    }
                                }
                            }
                        }
                    undoHelper.undoIndex =
                        undoHelper.undoIndex?.let { if (it == 0) null else it.minus(1) }
                }

            }

            MainScreenEvents.OnToggleBottomBar -> {
                _state.value = state.value.copy(bottomBarState = !state.value.bottomBarState)
            }
        }
    }

    private suspend fun onUpdateModule(
        moduleToUpdate: ModuleDisplayable,
        oldModule: ModuleDisplayable
    ) {
        addModule(moduleToUpdate)
        undoHelper.updateUndoList(
            listOf(
                Pair(
                    PerformedActionMarker.ActionUpdated(oldModule.toModuleDomain()),
                    moduleToUpdate.toModuleDomain()
                )
            )
        )
    }

    private fun getModules() {
        viewModelScope.launch {
            useCases.getModulesUseCase().collectLatest { list ->
                _state.value = state.value.copy(modules = list.map { ModuleDisplayable(it) })

            }
        }
    }

    private suspend fun addModule(module: ModuleDisplayable) {

        useCases.addModuleUseCase(module.toModuleDomain().copy(timeStamp = System.currentTimeMillis()))
    }

    private suspend fun addModules(modules: List<ModuleDisplayable>) {

        useCases.addModulesUseCase(modules.map { it.toModuleDomain() })
    }

    private fun deleteModule(module: ModuleDisplayable) {
        job = null
        job = viewModelScope.launch {
            useCases.deleteModuleUseCase(module.toModuleDomain())
        }
    }

    private suspend fun deleteModules(module: List<ModuleDisplayable>) {
        useCases.deleteModulesUseCase(module.map { it.toModuleDomain() })
    }

    private suspend fun fetchModulesFromRemote(): Flow<ApiResult> {
        return useCases.fetchModulesFromRemoteUseCase()
    }

    private suspend fun pushModulesToRemote(): Flow<ApiResult> {
        return useCases.pushModulesToRemoteUseCase()
    }

    private suspend fun onAddNewIncrementationFromDate(
        eventModule: ModuleDisplayable,
        newModuleToInsert: ModuleDisplayable
    ) {

        val skippedModuleToUpdate =
            eventModule.copy(isSkipped = true, newIncrementation = null)

        val skippedModuleToInsert = newModuleToInsert.copy(
            id = UUID.randomUUID(),
            newIncrementation = eventModule.newIncrementation
        )
        if (skippedModuleToInsert.newIncrementation != null) {

            val newIncrementation =
                skippedModuleToInsert.incrementation.toInt() + skippedModuleToInsert.newIncrementation

            val newEpochDay = LocalDate.ofEpochDay(skippedModuleToInsert.epochDay)
                .plusDays(newIncrementation.toLong()).toEpochDay()

            val moduleToAdd = skippedModuleToInsert.copy(
                newIncrementation = null,
                incrementation = newIncrementation.toString(),
                epochDay = newEpochDay,
                id = UUID.randomUUID()
            )
            val modules = listOf(
                skippedModuleToUpdate,
                skippedModuleToInsert,
                moduleToAdd
            )
            addModules(modules)
            undoHelper.updateUndoList(
                listOf(
                    Pair(
                        PerformedActionMarker.ActionUpdated(
                            skippedModuleToUpdate.copy(isSkipped = !skippedModuleToUpdate.isSkipped)
                                .toModuleDomain()
                        ),
                        skippedModuleToUpdate.toModuleDomain()
                    ),
                    Pair(PerformedActionMarker.ActionAdded, skippedModuleToInsert.toModuleDomain()),
                    Pair(PerformedActionMarker.ActionAdded, moduleToAdd.toModuleDomain()),
                )
            )
        }
        _state.value = state.value.copy(newModuleToInsert = null)

    }

    private suspend fun onAddNewIncrementation(moduleToUpdate: ModuleDisplayable) {
        if (moduleToUpdate.newIncrementation != null) {
            val newIncrementation =
                moduleToUpdate.incrementation.toInt() + moduleToUpdate.newIncrementation
            val newEpochDay = LocalDate.ofEpochDay(moduleToUpdate.epochDay)
                .plusDays(newIncrementation.toLong()).toEpochDay()
            val moduleToAdd = moduleToUpdate.copy(
                newIncrementation = null,
                incrementation = newIncrementation.toString(),
                epochDay = newEpochDay,
                id = UUID.randomUUID()
            )
            val modules = listOf(moduleToUpdate, moduleToAdd)
            addModules(modules)
            undoHelper.updateUndoList(
                listOf(
                    Pair(
                        PerformedActionMarker.ActionUpdated(
                            moduleToUpdate.copy(newIncrementation = null).toModuleDomain()
                        ),
                        moduleToUpdate.toModuleDomain()
                    ),
                    Pair(PerformedActionMarker.ActionAdded, moduleToAdd.toModuleDomain())
                )
            )
        }
    }

    fun dropDatabase() {
        job = null
        job = viewModelScope.launch {
            useCases.dropDataBase()
        }
    }

    private fun updateLastCard(cardNumber: Int) {
        job = null
        job = viewModelScope.launch {
            useCases.saveLastCardUseCase(cardNumber)
            _state.value = state.value.copy(currentPage = cardNumber)
        }
    }

    fun isUndoButtonEnabled() = undoHelper.undoIndex != null

    fun isRedoButtonEnabled() =
        undoHelper.undoList.isNotEmpty() && undoHelper.undoIndex != undoHelper.undoList.size - 1

}