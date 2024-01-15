package com.example.module.presentation.utils

import com.example.module.domain.model.ModuleDomain

data class ModuleScreenState(
    val module: ModuleDomain? = null,
    val isNameEditEnabled: Boolean = false,
    val isCommentEditEnabled: Boolean = false,
    val isColorDropdownMenuVisible: Boolean = false,
    val isTextDropdownMenuVisible: Boolean = false
)
