package com.example.module.presentation.utils

sealed interface AllModulesPreviewScreenEvents {
    object OnDeleteButtonClick : AllModulesPreviewScreenEvents
    object OnDeleteDialogDismiss : AllModulesPreviewScreenEvents
    object OnConfirmDeleteButtonClick : AllModulesPreviewScreenEvents
}