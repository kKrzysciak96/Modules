package com.example.module.presentation.utils

import com.example.module.domain.model.ModuleDomain

sealed interface ModuleScreenEvents {

    object OnEditNameToggle : ModuleScreenEvents
    data class OnNameEntered(val name: String) : ModuleScreenEvents
    object OnEditCommentToggle : ModuleScreenEvents
    data class OnCommentEntered(val comment: String) : ModuleScreenEvents
    data class OnModuleSaveButtonClick(val module: ModuleDomain) : ModuleScreenEvents
    object OnBackButtonPress : ModuleScreenEvents
    object OnToggleColorsClick : ModuleScreenEvents
    object OnColorMenuDismissRequest : ModuleScreenEvents
    object OnToggleFontSizeClick : ModuleScreenEvents
    object OnFontSizeMenuDismissRequest : ModuleScreenEvents
}