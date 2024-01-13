package com.example.module.presentation.utils

sealed interface ModuleScreenEvents {

    object OnEditNameToggle : ModuleScreenEvents
    data class OnNameEntered(val name: String) : ModuleScreenEvents
    object OnEditCommentToggle : ModuleScreenEvents
    data class OnCommentEntered(val comment: String) : ModuleScreenEvents
    data class OnModuleSaveButtonClick(val module: Module) : ModuleScreenEvents
    object OnBackButtonPress : ModuleScreenEvents
    object OnToggleColorsClick : ModuleScreenEvents
    object OnColorMenuDismissRequest : ModuleScreenEvents
    object OnToggleFontSizeClick : ModuleScreenEvents
    object OnFontSizeMenuDismissRequest : ModuleScreenEvents
}