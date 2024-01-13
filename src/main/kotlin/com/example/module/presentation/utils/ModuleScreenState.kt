package com.example.module.presentation.utils

data class ModuleScreenState(
    val module: Module? = null,
    val isNameEditEnabled: Boolean = false,
    val isCommentEditEnabled: Boolean = false,
    val isColorDropdownMenuVisible: Boolean = false,
    val isTextDropdownMenuVisible: Boolean = false
)
